DESCRIPTION = "TensorFlow Lite Standalone Pip"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"
# Compute branch info from ${PV} as Base PV...
BPV = "${@'.'.join(d.getVar('PV').split('.')[0:2])}"
DPV = "${@'.'.join(d.getVar('PV').split('.')[0:3])}"

SRCREV_tensorflow = "3f878cff5b698b82eea85db2b60d65a2e320850e"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;name=tensorflow;branch=r${BPV};protocol=https \
"

S = "${WORKDIR}/git"

DEPENDS += "\
            python3-pip-native \
            python3-wheel-native \
            python3-numpy \
            python3-pybind11 \
"

RDEPENDS:${PN} += " \
    python3 \
    python3-numpy \
"

inherit setuptools3 cmake 
export https_proxy

TENSORFLOW_LITE_BUILD_DIR = "${S}/tensorflow/lite/tools/pip_package/gen/tflite_pip/python3"
TENSORFLOW_LITE_DIR = "${S}/tensorflow/lite"

PYBIND11_INCLUDE = "${PYTHON_INCLUDE_DIR}/pybind11"
NUMPY_INCLUDE = "${PKG_CONFIG_SYSROOT_DIR}/${PYTHON_SITEPACKAGES_DIR}/numpy/core/include"

OECMAKE_SOURCEPATH = "${TENSORFLOW_LITE_DIR}"
OECMAKE_TARGET_COMPILE= "_pywrap_tensorflow_interpreter_wrapper"
OECMAKE_C_FLAGS += "-I${PYTHON_INCLUDE_DIR} -I${PYBIND11_IN} -I${NUMPY_INCLUDE}"
OECMAKE_CXX_FLAGS += "-I${PYTHON_INCLUDE_DIR} -I${PYBIND11_INCLUDE} -I${NUMPY_INCLUDE}"
CMAKE_VERBOSE = "VERBOSE=1"

# Note:
# XNNPack is valid only on 64bit. 
# In the case of arm 32bit, it will be turned off because the build will be
# an error depending on the combination of target CPUs.

# Note:
# Download the submodule using FetchContent_Populate.
# Therefore, turn off FETCHCONTENT_FULLY_DISCONNECTED.
EXTRA_OECMAKE:append = " -DFETCHCONTENT_FULLY_DISCONNECTED=OFF"

HOST_ARCH:raspberrypi4-64 = "aarch64"
TUNE_CCARGS:raspberrypi4-64 = ""
EXTRA_OECMAKE:append:raspberrypi4-64 = " -DTFLITE_ENABLE_XNNPACK=ON"

do_configure[network] = "1"

do_compile:prepend() {
    TENSORFLOW_VERSION=$(grep "_VERSION = " "${S}/tensorflow/tools/pip_package/setup.py" | cut -d= -f2 | sed "s/[ '-]//g")
    export PACKAGE_VERSION="${TENSORFLOW_VERSION}"

    rm -rf "${TENSORFLOW_LITE_BUILD_DIR}" && mkdir -p "${TENSORFLOW_LITE_BUILD_DIR}/tflite_runtime"
    cp -r "${TENSORFLOW_LITE_DIR}/tools/pip_package/debian" \
          "${TENSORFLOW_LITE_DIR}/tools/pip_package/MANIFEST.in" \
          "${TENSORFLOW_LITE_DIR}/python/interpreter_wrapper" \
          "${TENSORFLOW_LITE_BUILD_DIR}"
    cp "${TENSORFLOW_LITE_DIR}/tools/pip_package/setup_with_binary.py" "${TENSORFLOW_LITE_BUILD_DIR}/setup.py"
    cp "${TENSORFLOW_LITE_DIR}/python/interpreter.py" \
       "${TENSORFLOW_LITE_DIR}/python/metrics/metrics_interface.py" \
       "${TENSORFLOW_LITE_DIR}/python/metrics/metrics_portable.py" \
       "${TENSORFLOW_LITE_BUILD_DIR}/tflite_runtime"
    echo "__version__ = '${PACKAGE_VERSION}'" >> "${TENSORFLOW_LITE_BUILD_DIR}/tflite_runtime/__init__.py"
    echo "__git_version__ = '$(git -C "${S}" describe)'" >> "${TENSORFLOW_LITE_BUILD_DIR}/tflite_runtime/__init__.py"
}

do_compile:append() {
    cp "${B}/_pywrap_tensorflow_interpreter_wrapper.so" \
        "${TENSORFLOW_LITE_BUILD_DIR}/tflite_runtime"

    cd "${TENSORFLOW_LITE_BUILD_DIR}"
    echo ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN}

    echo "build aarch64"
    export TENSORFLOW_TARGET=aarch64
    export TARGET=aarch64

    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} setup.py \
    bdist bdist_wheel
}

do_install() {
    echo "Generating pip package"
    cd "${TENSORFLOW_LITE_BUILD_DIR}"

    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}

    echo ${D}/${PYTHON_SITEPACKAGES_DIR}

    TAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    PYTHONPATH=${D}${PYTHON_SITEPACKAGES_DIR} \
    ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} -m pip install --disable-pip-version-check -v \
    -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir --no-deps \
    ${S}/tensorflow/lite/tools/pip_package/gen/tflite_pip/python3/dist/tflite_runtime-${DPV}-*.whl

}

FILES:${PN}-dev = ""
INSANE_SKIP:${PN} += "dev-so"
FILES:${PN} += "${libdir}/* ${datadir}/*"

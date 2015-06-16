DESCRIPTION = "TI OpenCL Examples"
HOMEPAGE = "https://gitorious.design.ti.com/ocl"
LICENSE = "BSD"

include ocl.inc

PR = "${INC_PR}.0"

COMPATIBLE_MACHINE = "dra7xx|k2hk-evm"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "opencl ti-cgt6x-native clocl-native"

RDEPENDS_${PN} += " opencl-runtime"

S = "${WORKDIR}/git/opencl_example_src"
B = "${S}"

OCL_EXAMPLE_LIST = "buffer ccode dsplib_fft float_compute null offline offline_embed platforms simple vecadd matmpy ooo_callback edmamgr"

python do_unpack_append() {
    import shutil

    git_dir = d.expand("${WORKDIR}/git/examples")
    s = d.getVar("S", True)

    os.makedirs(s)
    shutil.copy(os.path.join(git_dir,"Makefile"),s)
    shutil.copy(os.path.join(git_dir,"make.inc"),s)
    for example in d.getVar("OCL_EXAMPLE_LIST").split():
        shutil.copytree(os.path.join(git_dir,example), os.path.join(s,example))
}


EXTRA_OEMAKE = " TARGET_ROOTDIR=${STAGING_DIR_HOST} TI_OCL_CGT_INSTALL=${STAGING_DIR_NATIVE}/usr/share/ti/cgt-c6x"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${datadir}/ti/examples/opencl

    install ${B}/Makefile ${D}${datadir}/ti/examples/opencl
    install ${B}/make.inc ${D}${datadir}/ti/examples/opencl

    for ocl_example in ${OCL_EXAMPLE_LIST}; do
        install -d ${D}${datadir}/ti/examples/opencl/${ocl_example}
        cp -rv ${B}/${ocl_example}/* ${D}${datadir}/ti/examples/opencl/${ocl_example}
    done
}

FILES_${PN} += "\
    ${datadir}/ti/examples/opencl \
"

FILES_${PN}-dbg += "\
    ${datadir}/ti/examples/opencl/*/.debug \
"

INSANE_SKIP_${PN} = "arch ldflags textrel"

CREATE_SRCIPK = "1"
SRCIPK_INSTALL_DIR = "example-applications/${PN}-${PV}"

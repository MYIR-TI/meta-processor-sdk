DESCRIPTION = "Firmware for DSP for an example application called copycodectest"
LICENSE = "TI-TSPA"
LIC_FILES_CHKSUM = "file://src/ti/framework/dce/dce.c;startline=1;endline=31;md5=2c6e9aba6ed75f22b1a2b7544b1c809d"

COMPATIBLE_MACHINE = "dra7xx"

SRC_URI = "git://git.ti.com/glsdk/dspdce.git;protocol=git"

SRCREV = "71e8fbf8e4f91b60680cf18a0c202a222e9ae3ba"

PV = "1.00.00.06"

S = "${WORKDIR}/git"

require recipes-ti/includes/ti-paths.inc
require recipes-ti/includes/ti-staging.inc

PR = "r0"

DEPENDS = "ti-xdctools ti-sysbios ti-codec-engine ti-framework-components ti-xdais ti-ipc-rtos ti-osal ti-cgt6x-native"

export HWVERSION="ES10"
export BIOSTOOLSROOT="${STAGING_DIR_TARGET}/usr/share/ti"

export XDCVERSION="ti-xdctools-tree"
export BIOSVERSION="ti-sysbios-tree"
export IPCVERSION="ti-ipc-tree"
export CEVERSION="ti-codec-engine-tree"
export FCVERSION="ti-framework-components-tree"
export XDAISVERSION="ti-xdais-tree"
export OSALVERSION="ti-osal-tree"

export IPCSRC="${STAGING_DIR_TARGET}/usr/share/ti/ti-ipc-tree"
export C66XCGTOOLSPATH="${STAGING_DIR_NATIVE}/usr/share/ti/cgt-c6x"

do_configure() {
    cd ${S}
    make unconfig
    make vayu_config
}

do_compile() {
    cd ${S}
    make dspbin
}

TARGET = "dra7-dsp1-fw.xe66.dce"

do_install() {
        mkdir -p ${D}${base_libdir}/firmware
        cp ${S}/dra7xx-c66x-dsp.xe66 ${D}${base_libdir}/firmware/${TARGET}
}

INSANE_SKIP_${PN} = "arch"

FILES_${PN} += "${base_libdir}/firmware/${TARGET}"

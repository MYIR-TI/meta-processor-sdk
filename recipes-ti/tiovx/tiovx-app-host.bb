SUMMARY = "TI OpenVX HOST (Linux A15) conformance verification application, Khronos tutorial example, and Host side IPC implementation "
DESCRIPTION = "TI OpenVX implementation, TIOVX, includes Khronos defined conformance test and tutorial example, as well as additional TI specific tests. This package creates application which runs all the conformance tests (7K-8K) and Khronos tutorial exercise1. Same package includes IPC implementation (MessageQ based) needed for communication with DSP firmware (loaded at boot time) "

LICENSE = "BSD-3-Clause & MIT"
LIC_FILES_CHKSUM = "file://docs/manifest/TIOVX-APP-HOST_01.00.01.00_manifest.html;md5=247d7c56d783f583bf802490d5c93db3"

require recipes-ti/includes/tisdk-paths.inc
require tiovx-sys.inc

DEPENDS = " tiovx-sys-iface tiovx-lib-host ti-ipc cmem opencv"
RDEPENDS_${PN} = " tiovx-sys-iface tiovx-sys-iface-firmware tiovx-lib-host ti-ipc cmem "

PR = "r0"

COMPATIBLE_MACHINE = "dra7xx"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROCLIST = "host khronos_example"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += " TARGET_ROOTDIR=${STAGING_DIR_HOST} "
EXTRA_OEMAKE += " GCCLINARO=${TOOLCHAIN_PATH} TIOVXPATH=${TIOVX_INSTALL_DIR} IPCPATH=${IPC_INSTALL_DIR} "
EXTRA_OEMAKE += " SDKPLATFORMIFPATH=${TIOVX_INSTALL_DIR}/sys-iface "
EXTRA_OEMAKE += " PROC_LIST='${PROCLIST}' "

do_install () {
    install -d ${D}${bindir}/
    install -m 0755 ${S}/host/bin/debug/app_host ${D}${bindir}/tiovx-app_host
    install -m 0755 ${S}/khronos_example/bin/debug/opticalflow ${D}${bindir}/tiovx-opticalflow
}

FILES_${PN} += "${bindir}/*"
INSANE_SKIP_${PN} = "ldflags"

DESCRIPTION = "TI OpenCL"
HOMEPAGE = "https://gitorious.design.ti.com/ocl"
LICENSE = "BSD"

include ocl.inc
require recipes-ti/includes/ti-paths.inc

PR = "${INC_PR}.0"

inherit cmake

DEPENDS = "ti-llvm3.3-native common-csl-ip-rtos ti-xdctools ti-ipc-rtos ti-sysbios ti-cgt6x-native edma3-lld-rtos ti-xdais ti-framework-components libulm gdbserver-c6x"

COMPATIBLE_MACHINE = "dra7xx|k2hk-evm"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git/monitor"
S_dra7xx = "${WORKDIR}/git/monitor_vayu"

EXTRA_OECMAKE += "-DCROSS_COMPILE=TRUE -DOCL_MONITOR_DIR=${S} -DBUILD_OUTPUT=all -DSHARE_PATH=${datadir}/ti"

export TI_OCL_CGT_INSTALL = "${STAGING_DIR_NATIVE}/usr/share/ti/cgt-c6x"
export PDK_DIR = "${PDK_INSTALL_DIR}"
export IPC_DIR = "${IPC_INSTALL_DIR}"
export XDC_DIR = "${XDC_INSTALL_DIR}"
export BIOS_DIR = "${SYSBIOS_INSTALL_DIR}"
export EDMA3LLD_DIR = "${EDMA3_LLD_INSTALL_DIR}"
export XDAIS_DIR = "${XDAIS_INSTALL_DIR}"
export FC_DIR = "${FC_INSTALL_DIR}"
export ULM_DIR ="${STAGING_DIR_TARGET}/usr/share/ti/ulm"
export GDB_SERVER_DIR = "${STAGING_DIR_TARGET}/usr/share/ti/gdbc6x"
export X86_LLVM_DIR = "${STAGING_DIR_NATIVE}/usr"
export XDCPATH = "${S};$(IPC_DIR)/packages;$(BIOS_DIR)/packages;$(EDMA3LLD_DIR)/packages;$(FC_DIR)/packages;$(XDAIS_DIR)/packages"

MONITOR_FIRMWARE = ""
MONITOR_FIRMWARE_dra7xx = "${base_libdir}/firmware/*"

FILES_${PN} += " \
    ${datadir}/ti/opencl/* \
    ${MONITOR_FIRMWARE} \
"

INSANE_SKIP_${PN} = "arch"

PR = "r0"
DESCRIPTION = "Package containing Makefile and Rules.make file for TISDKs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Build the list of component makefiles to put together to build the
# Makefile that goes into the SDK.
#
# It is assumed that the component makefiles follow the naming
# Makefile_$component.  All Makefiles will be part of the SRC_URI to be
# fetched, but only the listed ones will be used to build the final Makefile

SRC_URI = "\
    file://Makefile \
    file://Makefile_am-sysinfo \
    file://Makefile_arm-benchmarks \
    file://Makefile_barcode-roi \
    file://Makefile_boot-monitor \
    file://Makefile_cmem-mod \
    file://Makefile_cryptodev \
    file://Makefile_dual-camera-demo \
    file://Makefile_evse-hmi \
    file://Makefile_image-gallery \
    file://Makefile_linux \
    file://Makefile_linux-dtbs \
    file://Makefile_linux-fitimage \
    file://Makefile_matrix-gui \
    file://Makefile_matrix-gui-browser \
    file://Makefile_mmwavegesture-hmi \
    file://Makefile_oprofile-example \
    file://Makefile_pdm-anomaly-detection \
    file://Makefile_protection-relays-hmi \
    file://Makefile_pru-adc \
    file://Makefile_pru-icss \
    file://Makefile_quick-playground \
    file://Makefile_sysfw-image \
    file://Makefile_ti-crypto-examples \
    file://Makefile_tidl-examples \
    file://Makefile_ti-sgx-ddk-km \
    file://Makefile_u-boot-legacy \
    file://Makefile_uio-module-drv \
    file://Makefile_u-boot-spl \
    file://Rules.make \    
"

SRC_URI:append:am62xx = "\
    file://Makefile_ti-img-rogue-driver \
"

SRC_URI:append:am65xx = "\
    file://Makefile_sysfw-image-am65xx \
"

SRC_URI:append:am65xx-evm = "\
    file://Makefile_ti-sgx-ddk-km-am65xx \
"

SRC_URI:remove:am65xx-evm = "\
    file://Makefile_matrix-gui \
    file://Makefile_matrix-gui-browser \
    file://Makefile_pru \
    file://Makefile_pru-icss \
    file://Makefile_barcode-roi \
    file://Makefile_mmwavegesture-hmi \
    file://Makefile_pdm-anomaly-detection \
    file://Makefile_ti-sgx-ddk-km \
"

MAKEFILES_MATRIX_GUI = "matrix-gui-browser \
"

MAKEFILES_MATRIX_GUI:am64xx = ""

MAKEFILES_COMMON = "linux \
                    matrix-gui \
                    arm-benchmarks \
                    am-sysinfo \
                    oprofile-example \
                    ${MAKEFILES_MATRIX_GUI} \
"

MAKEFILES_COMMON:remove:am65xx-evm = "\
    matrix-gui \
    matrix-gui-browser \
"

MAKEFILES = ""

# This example application should not be used when using non-SGX
QUICK_PLAYGROUND = "${@oe.utils.conditional('ARAGO_QT_PROVIDER','qt4-embedded-gles','quick-playground','', d)}"

# Add device specific make targets

MAKEFILES:append:ti33x = " u-boot-spl \
                           ${QUICK_PLAYGROUND} \
                           ti-crypto-examples \
                           linux-dtbs \
                           cryptodev \
                           ti-sgx-ddk-km \
                           pru-icss \
                           uio-module-drv \
                           evse-hmi \
			   mmwavegesture-hmi \
                           pdm-anomaly-detection \
                           protection-relays-hmi \
"

MAKEFILES:append:am335x-evm = " pru-adc \
"

MAKEFILES:append:ti43x = " u-boot-spl \
                           ${QUICK_PLAYGROUND} \
                           ti-crypto-examples \
                           linux-dtbs \
                           cryptodev \
                           dual-camera-demo \
                           image-gallery \
                           evse-hmi \
                           ti-sgx-ddk-km \
                           cmem-mod \
                           pru-icss \
                           uio-module-drv \
			   mmwavegesture-hmi \
                           pdm-anomaly-detection \
"

MAKEFILES:append:k3 = " u-boot-spl \
                        linux-dtbs \
                        cryptodev \
                        sysfw-image \
"

MAKEFILES:append:am64xx = " \
                        pru-icss \
                        linux-fitimage \
"

MAKEFILES:append:am62xx = " \
                        pru-icss \
                        linux-fitimage \
                        ti-img-rogue-driver \
"

MAKEFILES:append:am65xx-evm = " ti-sgx-ddk-km-am65xx \
"

MAKEFILES:remove:am65xx-evm = " sysfw-image"
MAKEFILES:remove:am65xx-hs-evm = " sysfw-image"
MAKEFILES:append:am65xx-evm = " sysfw-image-am65xx"
MAKEFILES:append:am65xx-hs-evm = " sysfw-image-am65xx"

MAKEFILES:remove:ti33x = " barcode-roi"

MAKEFILES:remove:ti43x = " barcode-roi"

MAKEFILES:remove:ti33x = "${@bb.utils.contains('MACHINE_FEATURES', 'gpu', '', 'ti-sgx-ddk-km', d)}"
MAKEFILES:remove:ti43x = "${@bb.utils.contains('MACHINE_FEATURES', 'gpu', '', 'ti-sgx-ddk-km', d)}"


# Use this to export kernel arch to ARCH
#
# We need to be very careful here. This class will also overwrite UBOOT_ARCH
# even though it may be set in the machine configuration.
inherit kernel-arch

# Use ARCH format expected by the makefile
PLATFORM_ARCH = "${ARMPKGARCH}"
PLATFORM_ARCH:arm = "armv7-a"

# ti-sgx-ddk-km configurations
# See meta-ti/recipes-bsp/powervr-drivers/ti-sgx-ddk-km_1.17.4948957.bb
TI_SGX_TARGET_PRODUCT = ""
TI_SGX_TARGET_PRODUCT:ti33x = "ti335x"
TI_SGX_TARGET_PRODUCT:ti43x = "ti437x"
TI_SGX_TARGET_PRODUCT:k3 = "ti654x"

TI_SGX_TARGET_ARCH = "armhf"
TI_SGX_TARGET_ARCH:k3 = "aarch64"

PLATFORM_DEBUGSS = ""

PLATFORM_GDBSERVERPROXY = ""

PRU_ICSS_INSTALL_TARGET = "pru-icss_install_none"
PRU_ICSS_INSTALL_TARGET:ti33x = "pru-icss_install_am335x"
PRU_ICSS_INSTALL_TARGET:ti43x = "pru-icss_install_am437x"
PRU_ICSS_INSTALL_TARGET:am65xx = "pru-icss_install_am65x"
PRU_ICSS_INSTALL_TARGET:am64xx = "pru-icss_install_am64x"
PRU_ICSS_INSTALL_TARGET:am62xx = "pru-icss_install_am62x"

# Path to toolchains for the various cores in TI SOCs
#
# These are provided by the TI RTOS SDK and used to build firmwares used by the
# IPC Linux examples.
IPC_TOOLS_PATHS_C66 = "ti.targets.elf.C66="\$\(C6X_GEN_INSTALL_PATH\)""
IPC_TOOLS_PATHS_M4  = "ti.targets.arm.elf.M4="\$\(TOOLCHAIN_PATH_M4\)" ti.targets.arm.elf.M4F="\$\(TOOLCHAIN_PATH_M4\)""
IPC_TOOLS_PATHS_R5F  = "ti.targets.arm.elf.R5F="\$\(TOOLCHAIN_PATH_R5\)""
IPC_TOOLS_PATHS_C674 = "ti.targets.elf.C674="\$\(C6X_GEN_INSTALL_PATH\)""

IPC_TOOLS_PATHS = ""
IPC_TOOLS_PATHS:append:k3 = "${IPC_TOOLS_PATHS_R5F}"

# If it's not defined at all, like for zImage case
UBOOT_LOADADDRESS ?= "0"

KERNEL_BUILD_CMDS = "${@oe.utils.conditional('KERNEL_IMAGETYPE','uImage','LOADADDR=${UBOOT_LOADADDRESS}','',d)} ${KERNEL_IMAGETYPE}"

DEFCONFIG = "defconfig"

AMSDK_DEFCONFIG = "singlecore-omap2plus_defconfig"

DEFCONFIG := "${@oe.utils.conditional('ARAGO_BRAND','amsdk','${AMSDK_DEFCONFIG}','${DEFCONFIG}',d)}"

EXTERNAL_TOOLCHAIN_BINDIR = "/usr/bin"
INTERNAL_TOOLCHAIN_BINDIR = "/usr/bin/${TARGET_ARCH}${TARGET_VENDOR}-${TARGET_OS}"

# This step will stitch together the final Makefile based on the supported
# make targets.
do_install () {
    install -d ${D}/

    # Start with the base Makefile
    install  ${WORKDIR}/Makefile ${D}/Makefile

    # Remember the targets added so we can update the all target
    targets=""
    clean_targets=""
    install_targets=""

    # Now add common build targets
    for x in ${MAKEFILES_COMMON}
    do
        cat ${WORKDIR}/Makefile_${x} >> ${D}/Makefile
        targets="$targets""$x\ "
        clean_targets="$clean_targets""$x""_clean\ "
        install_targets="$install_targets""$x""_install\ "
    done

    # Now add device specific build targets
    for x in ${MAKEFILES}
    do
        cat ${WORKDIR}/Makefile_${x} >> ${D}/Makefile
        targets="$targets""$x\ "
        clean_targets="$clean_targets""$x""_clean\ "
        install_targets="$install_targets""$x""_install\ "
    done

    # Update the all, clean, and install targets if we added targets
    if [ "$targets" != "" ]
    then
        sed -i -e "s/__ALL_TARGETS__/$targets/" ${D}/Makefile
        sed -i -e "s/__CLEAN_TARGETS__/$clean_targets/" ${D}/Makefile
        sed -i -e "s/__INSTALL_TARGETS__/$install_targets/" ${D}/Makefile
    fi

    sed -i -e "s/__KERNEL_ARCH__/${ARCH}/" ${D}/Makefile
    sed -i -e "s/__KERNEL_IMAGE_TYPE__/${KERNEL_IMAGETYPE}/" ${D}/Makefile
    sed -i -e "s/__KERNEL_BUILD_CMDS__/${KERNEL_BUILD_CMDS}/" ${D}/Makefile
    sed -i -e "s/__SDKMACHINE__/${SDKMACHINE}/g" ${D}/Makefile

    sed -i -e "s/__TI_SGX_TARGET_PRODUCT__/${TI_SGX_TARGET_PRODUCT}/" ${D}/Makefile
    sed -i -e "s/__TI_SGX_TARGET_ARCH__/${TI_SGX_TARGET_ARCH}/" ${D}/Makefile
    sed -i -e "s/__BOOT_MONITOR_MAKE_TARGET__/${BOOT_MONITOR_MAKE_TARGET}/g" ${D}/Makefile
    sed -i -e "s/__PRU_ICSS_INSTALL_TARGET__/${PRU_ICSS_INSTALL_TARGET}/g" ${D}/Makefile
    sed -i -e "s/__IPC_TOOLS_PATHS__/${IPC_TOOLS_PATHS}/g" ${D}/Makefile
    sed -i -e "s/__TISDK_VERSION__/${TISDK_VERSION}/g" ${D}/Makefile

    cat ${D}/Makefile | grep "__DTB_DEPEND__" > /dev/null

    if [ "$?" == "0" ]
    then
        sed -i -e "s|__KERNEL_DEVICETREE__|${KERNEL_DEVICETREE}|" ${D}/Makefile
        sed -i -e "s/__DTB_DEPEND__/linux-dtbs/" ${D}/Makefile
        sed -i -e "s/__DTB_DEPEND_INSTALL__/linux-dtbs_install/" ${D}/Makefile
    else
        sed -i -e "s/__DTB_DEPEND__//" ${D}/Makefile
        sed -i -e "s/__DTB_DEPEND_INSTALL__//" ${D}/Makefile
    fi


    install  ${WORKDIR}/Rules.make ${D}/Rules.make

    # fixup Rules.make values
    sed -i -e "s/__PLATFORM__/${MACHINE}/" ${D}/Rules.make
    sed -i -e "s/__DEFCONFIG__/${DEFCONFIG}/" ${D}/Rules.make
    sed -i -e "s/__ARCH__/${PLATFORM_ARCH}/" ${D}/Rules.make
    sed -i -e "s/__TOOLCHAIN_PREFIX__/${TOOLCHAIN_SYS}-/" ${D}/Rules.make
    sed -i -e "s/__UBOOT_MACHINE__/${UBOOT_MACHINE}/" ${D}/Rules.make
    sed -i -e "s/__CFLAGS__/${TARGET_CC_ARCH}/" ${D}/Rules.make
    sed -i -e "s/__SDKMACHINE__/${SDKMACHINE}/" ${D}/Rules.make

    if [ "${TOOLCHAIN_TYPE}" = "internal" ]; then
        sed -i -e "s|__TOOLCHAIN_BINDIR__|${INTERNAL_TOOLCHAIN_BINDIR}|" ${D}/Rules.make
    else
        sed -i -e "s|__TOOLCHAIN_BINDIR__|${EXTERNAL_TOOLCHAIN_BINDIR}|" ${D}/Rules.make
    fi

}

K3_UBOOT_MACHINE_R5 = ""
K3_UBOOT_MACHINE_R5:am65xx-evm = "am65x_evm_r5_defconfig"
K3_UBOOT_MACHINE_R5:am65xx-hs-evm = "am65x_hs_evm_r5_defconfig"
K3_UBOOT_MACHINE_R5:am64xx-evm = "am64x_evm_r5_defconfig"
K3_UBOOT_MACHINE_R5:am64xx-hs-evm = "am64x_hs_evm_r5_defconfig"
K3_UBOOT_MACHINE_R5:am62xx-evm = "am62x_evm_r5_config"

do_install:append:k3() {
    cat >> ${D}/Rules.make << __EOF__

# Add CROSS_COMPILE and UBOOT_MACHINE for the R5
export CROSS_COMPILE_ARMV7=\$(LINUX_DEVKIT_PATH)/sysroots/${SDKMACHINE}-arago-linux/usr/bin/arm-none-linux-gnueabihf-
UBOOT_MACHINE_R5=${K3_UBOOT_MACHINE_R5}
__EOF__
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

# Populate UBOOT_MACHINE when UBOOT_CONFIG is used
# (see uboot-config.bbclass)
python() {
    ubootmachine = d.getVar("UBOOT_MACHINE", True)
    ubootconfigflags = d.getVarFlags('UBOOT_CONFIG')
    # The "doc" varflag is special, we don't want to see it here
    ubootconfigflags.pop('doc', None)

    if ubootmachine and ubootconfigflags:
        bb.warn('UBOOT_MACHINE = "%s", UBOOT_CONFIG(flags) = "%s"' % (ubootmachine, ubootconfigflags))
        raise bb.parse.SkipPackage("You cannot use UBOOT_MACHINE and UBOOT_CONFIG at the same time.")

    if not ubootconfigflags:
        return

    ubootconfig = (d.getVar('UBOOT_CONFIG', True) or "").split()
    if len(ubootconfig) > 0:
        for config in ubootconfig:
            for f, v in ubootconfigflags.items():
                if config == f:
                    items = v.split(',')
                    if items[0] and len(items) > 3:
                        raise bb.parse.SkipPackage('Only config,images,binary can be specified!')
                    # From u-boot-ti.inc, the last config is the default
                    # So keep overwriting UBOOT_MACHINE to get to the default
                    d.setVar('UBOOT_MACHINE', items[0])
                    break
    elif len(ubootconfig) == 0:
       raise bb.parse.SkipPackage('You must set a default in UBOOT_CONFIG.')
}

FILES:${PN} = "/*"

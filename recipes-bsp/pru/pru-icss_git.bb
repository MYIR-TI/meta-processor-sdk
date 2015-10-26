DESCRIPTION = "Programmable Real-time Unit Software Package"
HOMEPAGE = "http://processors.wiki.ti.com/index.php/PRU-ICSS"
LICENSE = "BSD-3-Clause & GPL-2.0 & PD"

LIC_FILES_CHKSUM = "file://PRU-Package-v4.0-Manifest.html;md5=5ea937e4ff2c924a735d42e61ad8cbe3"

BRANCH = "master"
SRC_URI = "git://git.ti.com/pru-software-support-package/pru-software-support-package.git;protocol=git;branch=${BRANCH}"
SRCREV = "476289eb7c3a91977bae84aea55c56f3120b48ea"

PV = "4.0.0.0+git${SRCPV}"
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "ti-cgt-pru-native"

S = "${WORKDIR}/git"

export PRU_CGT = "${STAGING_DIR_NATIVE}/usr/share/ti/cgt-pru"

SUBDIRS = "examples pru_cape/pru_fw lib/src labs"

do_compile() {
    for dir in ${SUBDIRS}
    do
        make -C ${S}/$dir
    done
}

# By default, do not install anything
do_install() {
    :
}

do_install_am335x-evm() {
    install -d ${D}/lib/firmware
    for i in 0 1
    do
        install -m 0644 ${S}/examples/am335x/PRU_RPMsg_Echo_Interrupt${i}/gen/PRU_RPMsg_Echo_Interrupt${i}.out \
                        ${D}/lib/firmware/am335x-pru${i}-fw
    done
}

do_install_am437x-evm() {
    install -d ${D}/lib/firmware
    for i in 0 1
    do
        install -m 0644 ${S}/examples/am437x/PRU_RPMsg_Echo_Interrupt${i}/gen/PRU_RPMsg_Echo_Interrupt${i}.out \
                        ${D}/lib/firmware/am437x-pru1_${i}-fw
    done
}

do_install_am57xx-evm() {
    install -d ${D}/lib/firmware
    for i in 1 2
    do
        for j in 0 1
        do
            install -m 0644 ${S}/examples/am572x/PRU_RPMsg_Echo_Interrupt${i}_${j}/gen/PRU_RPMsg_Echo_Interrupt${i}_${j}.out \
                            ${D}/lib/firmware/am57xx-pru${i}_${j}-fw
        done
    done
}

FILES_${PN} += "/lib/firmware"

INSANE_SKIP_${PN} = "arch"

CREATE_SRCIPK = "1"
SRCIPK_INSTALL_DIR = "example-applications/${PN}-${PV}"

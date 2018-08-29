DESCRIPTION = "PRU PRP firmware for AM57xx"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE.ti;md5=b5aebf0668bdf95621259288c4a46d76"

PV = "2.14.22"
PE = "1"
PR = "r0"

COMPATIBLE_MACHINE = "am57xx-evm|am57xx-hs-evm"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "049f406cdd7c8e7753513f816e7a5d82a7df8cdb"
BRANCH ?= "master"

SRC_URI = "git://git.ti.com/processor-sdk/processor-sdk-firmware.git;protocol=git;branch=${BRANCH}"

S = "${WORKDIR}/git"

TARGET = "am57xx-pru0-pruprp-fw.elf am57xx-pru1-pruprp-fw.elf"

do_install() {
	install -d ${D}${base_libdir}/firmware/ti-pruss
	for f in ${TARGET}; do
		install -m 0644 ${S}/ti-pruss/$f ${D}${base_libdir}/firmware/ti-pruss/$f
	done
}

FILES_${PN} = "${base_libdir}/firmware"

INSANE_SKIP_${PN} = "arch"

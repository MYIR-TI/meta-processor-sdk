PR_append = "-tisdk4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://setup-uboot-env-Enhance-to-parse-uEnv.patch \
    file://setup-uboot-env-am57xx-evm-Detect-USB-connection.patch \
    file://create-sdcard-Fix-misspelling.patch \
    file://create-sdcard-Increase-rootfs-size-with-3-partitions.patch \
"

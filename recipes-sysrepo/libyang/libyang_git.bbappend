LIC_FILES_CHKSUM = "file://LICENSE;md5=f3916d7d8d42a6508d0ea418cfff10ad"

SRC_URI = "git://github.com/CESNET/libyang.git;protocol=https;branch=devel"

PV = "2.0.256+git${SRCPV}"
SRCREV = "56d4e07ef1cdeab3eb2e6700247f83ec9148edcc"

DEPENDS = "libpcre2"

FILES_${PN} += "/usr/share/yang/modules/libyang/*"

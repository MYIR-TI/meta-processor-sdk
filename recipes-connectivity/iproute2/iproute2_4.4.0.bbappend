PR_append = ".tisdk1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " \
    file://0001-Add-support-of-hsr-v1.patch \
    file://0001-Added-support-for-selection-of-new-HSR-version.patch \
    file://0002-add-support-for-prp-similar-to-hsr.patch \
"

require recipes-core/images/tisdk-server-rootfs-image.bb

IMAGE_INSTALL += "\
    packagegroup-arago-tisdk-addons-extra \
"

export IMAGE_BASENAME = "tisdk-server-extra-rootfs-image"

IMAGE_FSTYPES = "tar.gz"

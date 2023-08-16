PR:append = ".psdk3"

IMAGE_INSTALL:remove:am65xx = " \
    packagegroup-arago-tisdk-matrix \
    packagegroup-arago-tisdk-matrix-extra \
"

IMAGE_INSTALL:append:am65xx = " \
    packagegroup-arago-gst-sdk-target \
"

IMAGE_INSTALL:append:am64xx = " \
    dropbear \
    linuxptp \
    iproute2 \
    resize-rootfs \
"

IMAGE_INSTALL:append:am62xx = " \
    libcamera \
    resize-rootfs \
    packagegroup-seva \
"

WIC_CREATE_EXTRA_ARGS:append = " --no-fstab-update"

# Extra boot files for WIC images
do_image_wic:append:am64xx-evm[depends] = " wifi-oob:do_deploy"
IMAGE_BOOT_FILES:append:am64xx-evm = " wificfg"
do_image_wic:append:am62xx-evm[depends] = " wifi-oob:do_deploy"
IMAGE_BOOT_FILES:append:am62xx-evm = " wificfg"
do_image_wic:append:am62xx-lp-evm[depends] = " wifi-oob:do_deploy"
IMAGE_BOOT_FILES:append:am62xx-lp-evm = " wificfg"

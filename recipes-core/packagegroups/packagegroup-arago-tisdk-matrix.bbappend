PR_append = "-tisdk30"

MATRIX_APPS_remove_omap-a15 = " \
    matrix-3d-demo-kmscubevideo \
"

MATRIX_COMMON_APPS_remove = " \
    matrix-gui-oprofile-demos \
"

MATRIX_APPS_append_omap-a15 += " \
    matrix-gui-apps-dual-camera \
    matrix-gui-apps-image-gallery \
"

MATRIX_APPS_append_dra7xx += " \
"

# Remove until ported to gstreamer 1.6
#    matrix-multimedia-demo-dsp66imgproc

MATRIX_APPS_append_keystone += " \
"

# Remove until ported to gstreamer 1.6
#    matrix-multimedia-demo-dsp66imgproc

MATRIX_APPS_append_k2g-evm = " matrix-opencl-demo-floatcompute \
                               matrix-opencl-demo-vecadd \
"

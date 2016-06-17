PR_append = ".tisdk16"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.4:${THISDIR}/files:"

SRC_URI_append = " \
    file://0001-ARM-dts-keystone-evm-add-DT-bindings-for-debugss-and.patch \
    file://0002-ti_config_fragments-connectivity-enable-usb-serial-o.patch \
    file://0003-soc-ti-qmss-fix-error-in-shared-access-of-QMSS-INTD-.patch \
    file://0004-ARM-dts-keystone-use-syscon-regmap-to-enable-shared-.patch \
    file://0005-soc-ti-fix-error-in-set_words-helper.patch \
    file://0006-lib-ktree-add-generic-tree-implementation.patch \
    file://0007-soc-ti-qmss-unmap-descriptors-without-knowing-descri.patch \
    file://0008-net-netcp-support-of-multiple-subqueues-per-interfac.patch \
    file://0009-soc-ti-qmss-add-support-of-queue-range-specific-push.patch \
    file://0010-soc-ti-saving-qmss-clock-in-driver-storage.patch \
    file://0011-ARM-dts-keystone-add-qmss-clock-name.patch \
    file://0012-soc-qmss-support-of-loading-different-types-of-firmw.patch \
    file://0013-soc-ti-add-support-of-loading-qos-firmware.patch \
    file://0014-soc-ti-add-support-of-Quality-of-Service-PDSP.patch \
    file://0015-net-netcp-Add-Keystone-NetCP-QoS-driver.patch \
    file://0016-ARM-dts-keystone-add-qos-support.patch \
    file://0017-ti_config_fragments-connectivity.cfg-Enable-QoS-on-K.patch \
    file://0018-soc-ti-qos-fix-error-in-misspelled-string-when-parsi.patch \
    file://0019-net-netcp-cpts-introduce-keystone-netcp-cpts-softwar.patch \
    file://0020-net-netcp-cpts-update-to-64bit-for-4.4.y.patch \
    file://0021-net-netcp-add-support-of-cpts.patch \
    file://0022-ARM-keystone-dts-add-gbe-and-10gbe-netcp-cpts-bindin.patch \
    file://0023-ti_config_fragments-connectivity.cfg-enable-keystone.patch \
    file://0024-net-netcp-cpts-disable-cpts-time-stamping-using-DT-p.patch \
    file://0025-PCI-keystone-enable-mrrs-quirk-for-K2G-PCIe-device-i.patch \
    file://0026-ARM-dts-k2g-Add-DT-bindings-for-PCIe-controller-driv.patch \
    file://0027-ARM-dts-k2g-enable-DT-bindings-in-EVM-dts-file.patch \
    file://0028-PCI-keystone-Add-error-IRQ-handler.patch \
    file://0029-PCI-keystone-Remove-unnecessary-goto-statement.patch \
    file://0030-ARM-dts-keystone-add-PCI-dt-binding-for-error-interr.patch \
    file://0031-ARM-dts-k2g-add-PCI-dt-binding-for-error-interrupt.patch \
    file://0032-soc-ti-add-missing-include-to-knav_dma.h.patch \
    file://0033-arm-move-aes_glue.h-to-the-arch-arm-include-asm-dire.patch \
    file://0034-Documentation-devicetree-bindings-arm-keystone-keyst.patch \
    file://0035-crypto-add-stub-keystone-crypto-accelerator-driver.patch \
    file://0036-crypto-ks2-add-SA-crypto-accelerator-definitions.patch \
    file://0037-crypto-ks2-read-driver-configuration-parameters-from.patch \
    file://0038-crypto-ks2-add-dma-resources-allocation-code.patch \
    file://0039-crypto-ks2-add-low-level-crypto-hardware-interface-c.patch \
    file://0040-crypto-ks2-add-reworked-SG-list-copy-routines.patch \
    file://0041-crypto-ks2-add-command-label-functions.patch \
    file://0042-crypto-ks2-add-processing-functions-and-aead-algorit.patch \
    file://0043-ARM-dts-k2hk-add-device-binding-for-crypto-accelerat.patch \
    file://0044-ARM-dts-k2l-add-device-binding-for-crypto-accelerato.patch \
    file://0045-ARM-dts-k2e-add-device-binding-for-crypto-accelerato.patch \
    file://0046-crypto-keystone-add-driver-statistics.patch \
    file://0047-crypto-keystone-Add-support-for-HW-RNG.patch \
    file://0048-ti_config_fragments-baseport.cfg-disable-RT_GROUP_SC.patch \
    file://0049-ARM-OMAP-DRA7-powerdomain-data-Set-L3init-and-L4per-.patch \
    file://0050-ARM-OMAP-DRA7-powerdomain-data-Remove-unused-pwrsts_.patch \
    file://0051-ARM-OMAP-DRA7-powerdomain-data-Remove-unused-pwrsts_.patch \
    file://0052-soc-ti-opp-domain-Fix-wrong-scaling-of-VDD-regulator.patch \
    file://0053-rapidio-use-kobj_to_dev.patch \
    file://0054-drivers-Initialize-resource-entry-to-zero.patch \
    file://0055-rapidio-rionet-add-capability-to-change-MTU.patch \
    file://0056-rapidio-tsi721-fix-hardcoded-MRRS-setting.patch \
    file://0057-rapidio-tsi721-add-check-for-overlapped-IB-window-ma.patch \
    file://0058-rapidio-tsi721-add-option-to-configure-direct-mappin.patch \
    file://0059-rapidio-tsi721_dma-fix-pending-transaction-queue-han.patch \
    file://0060-rapidio-add-query_mport-operation.patch \
    file://0061-rapidio-tsi721-add-query_mport-callback.patch \
    file://0062-rapidio-add-shutdown-notification-for-RapidIO-device.patch \
    file://0063-rapidio-tsi721-add-shutdown-notification-callback.patch \
    file://0064-rapidio-rionet-add-shutdown-event-handling.patch \
    file://0065-rapidio-rework-common-RIO-device-add-delete-routines.patch \
    file://0066-rapidio-move-net-allocation-into-core-code.patch \
    file://0067-rapidio-add-core-mport-removal-support.patch \
    file://0068-rapidio-tsi721-add-HW-specific-mport-removal.patch \
    file://0069-powerpc-fsl_rio-changes-to-mport-registration.patch \
    file://0070-rapidio-rionet-add-locking-into-add-remove-device.patch \
    file://0071-rapidio-rionet-add-mport-removal-handling.patch \
    file://0072-rapidio-add-lock-protection-for-doorbell-list.patch \
    file://0073-rapidio-move-rio_local_set_device_id-function-to-the.patch \
    file://0074-rapidio-move-rio_pw_enable-into-core-code.patch \
    file://0075-rapidio-add-global-inbound-port-write-interfaces.patch \
    file://0076-rapidio-tsi721-fix-locking-in-OB_MSG-processing.patch \
    file://0077-rapidio-add-outbound-window-support.patch \
    file://0078-rapidio-tsi721-add-outbound-windows-mapping-support.patch \
    file://0079-rapidio-tsi721-add-filtered-debug-output.patch \
    file://0080-rapidio-tsi721_dma-update-error-reporting-from-prep_.patch \
    file://0081-rapidio-tsi721_dma-fix-synchronization-issues.patch \
    file://0082-rapidio-tsi721_dma-fix-hardware-error-handling.patch \
    file://0083-rapidio-add-mport-char-device-driver.patch \
    file://0084-rapidio-fix-potential-NULL-pointer-dereference.patch \
    file://0085-rapidio-mport_cdev-fix-uapi-type-definitions.patch \
"

RDEPENDS_kernel-base_append_keystone = " netcp-sa-fw"

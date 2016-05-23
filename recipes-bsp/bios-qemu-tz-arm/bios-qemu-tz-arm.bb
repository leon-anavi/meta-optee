SUMMARY = "bios qemu tz arm"
LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/Makefile;md5=de48beeeac747d80fdeb86942cfb8cdc"

SRC_URI = "git://github.com/linaro-swg/bios_qemu_tz_arm.git;protocol=https;branch=master"
SRCREV = "dad8551a66d3ca121ad1f2e577f68dbb4b8e76db"

S = "${WORKDIR}/git"

SRC_URI += " \
	file://0001-cmdline-root-on-vda.patch \
	file://0002-disable-initramfs.patch \
"

inherit deploy

# native tool
do_configure() {
    :
}

# Upstream Makefile specify -mcpu=cortex-a15 which is not compatible with
# following:
TARGET_CC_ARCH_remove_qemuarm-virt = "-march=armv7-a"

do_compile[depends] += "virtual/kernel:do_deploy"
do_compile[depends] += "optee-os:do_deploy"

EXTRA_OEMAKE='-C ${S} \
	CC="${CC}" \
	CFLAGS="" \
	LDFLAGS="" \
	LD="${LD}" \
	OBJCOPY="${OBJCOPY}" \
	CROSS_COMPILE="${TARGET_PREFIX}" \
	O="${S}/out/bios-qemu" \
	BIOS_NSEC_BLOB="${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin" \
	BIOS_NSEC_ROOTFS="${DEPLOY_DIR_IMAGE}/agl-image-minimal-${MACHINE}.cpio.gz" \
	BIOS_SECURE_BLOB="${DEPLOY_DIR_IMAGE}/tee.bin" \
	PLATFORM_FLAVOR=virt'

do_compile() {
    oe_runmake
}

do_install() {
    :
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/out/bios-qemu/bios.bin ${DEPLOYDIR}
}

addtask do_deploy before build after do_compile

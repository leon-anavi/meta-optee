require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://github.com/linaro-swg/linux.git;protocol=https;nobranch=1;name=machine"

SRCREV_machine ?= "optee_v9beta_linux_v4.5"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.5:"
SRC_URI += "\
	file://defconfig \
"

LINUX_VERSION ?= "4.5.0"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "qemuarm|qemuarm64|qemuarm-virt"

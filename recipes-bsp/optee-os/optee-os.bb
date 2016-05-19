SUMMARY = "OP-TEE os"
LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=69663ab153298557a59c67a60a743e5b"

SRC_URI = "git://github.com/OP-TEE/optee_os;protocol=https;branch=master"
SRCREV = "2.0.0"

S = "${WORKDIR}/git"

SRC_URI += " \
	file://0001-disable-libgcc-detection.patch \
"

inherit deploy

do_configure() {
    :
}

EXTRA_OEMAKE='-C ${S} \
	CC="${CC}" \
	LD="${LD}" \
	CFLAGS="" \
	LDFLAGS="" \
	OBJCOPY="${OBJCOPY}" \
	O=${S}/out/arm \
	DEBUG=1 \
	CFG_TEE_CORE_LOG_LEVEL=3 \
	CROSS_COMPILE="${TARGET_PREFIX}" \
	CROSS_COMPILE_core="${TARGET_PREFIX}" \
	CROSS_COMPILE_ta_arm64="${TARGET_PREFIX}" \
	CROSS_COMPILE_ta_arm32="${TARGET_PREFIX}"'

do_compile() {
    oe_runmake
}

do_install() {
    :
}

# TODO: find another place
do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/out/arm/core/tee.bin ${DEPLOYDIR}

    install -d ${DEPLOYDIR}/export-ta_arm32/
    rsync -a ${B}/out/arm/export-ta_arm32 ${DEPLOYDIR}
}

addtask do_deploy before build after do_compile

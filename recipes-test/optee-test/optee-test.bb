SUMMARY = "OP-TEE test"
LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/host/LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/OP-TEE/optee_test;protocol=https;branch=master"
SRCREV = "2.0.0"

S = "${WORKDIR}/git"

SRC_URI += " \
	file://0001-fix-build-xtest1000.patch \
"

inherit deploy

do_configure() {
    :
}

DEPENDS = "optee-client"
do_compile[depends] += "optee-os:do_deploy"

EXTRA_OEMAKE='-C ${S} \
	CC="${CC}" \
	LD="${LD}" \
	OBJCOPY="${OBJCOPY}" \
	O=${S}/out \
	DEBUG=1 \
	CFG_TEE_CORE_LOG_LEVEL=3 \
	CROSS_COMPILE_HOST="${TARGET_PREFIX}" \
	CROSS_COMPILE_TA="${TARGET_PREFIX}" \
	TA_DEV_KIT_DIR="${DEPLOY_DIR_IMAGE}/export-ta_arm32" \
	OPTEE_CLIENT_EXPORT="${S}/../../../optee-client/1.0-r0/git/out/export" \
	COMPILE_NS_USER="32"'

do_compile() {
    oe_runmake # V=1
}

do_install() {
    install -d ${D}/usr/bin/
    install -m 0755 ${B}/out/xtest/xtest ${D}/usr/bin/

    install -d ${D}/lib/optee_armtz/
    find ${B}/out/ta/ -name "*.ta" -exec install -m 0444 '{}' ${D}/lib/optee_armtz/ \;
}

PROVIDES += "${PN}-ta"
PACKAGES =+ "${PN}-ta"

ALLOW_EMPTY_${PN}-ta = "1"
FILES_${PN}-ta += "/lib/optee_armtz/*"

SUMMARY = "OP-TEE hello world"
LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/README.md;md5=514167e1b60101fff93e23e026209315"

SRC_URI = "git://github.com/jenswi-linaro/lcu14_optee_hello_world.git;protocol=https;branch=master"
SRCREV = "d70e680c56668ca531769a416b627c4b1fe4761c"

S = "${WORKDIR}/git"

SRC_URI += " \
	file://0001-fixup-ta-out-dir-target-path.patch \
"

DEPENDS = "libteec"

do_compile[depends] += "optee-os:do_deploy"

EXTRA_OEMAKE='-C ${S} \
	CC="${CC}" \
	CPP="${CPP}" \
	LD="${LD}" \
	LDFLAGS="" \
	OBJCOPY="${OBJCOPY}" \
	O=${S}/out \
	DEBUG=1 \
	CROSS_COMPILE="${TARGET_PREFIX}" \
	TEEC_EXPORT="${STAGING_INCDIR}" \
	TA_DEV_KIT_DIR="${DEPLOY_DIR_IMAGE}/export-ta_arm32"'

do_compile() {
    oe_runmake -C ${S}/host
    oe_runmake -C ${S}/ta
}

do_install() {
    install -d ${D}/usr/bin/
    install -m 0755 ${B}/host/hello_world ${D}/usr/bin/

    install -d ${D}/lib/optee_armtz/
    install -m 0444 ${B}/out/*.ta ${D}/lib/optee_armtz/
}

PROVIDES += "${PN}-ta ${PN}-host"
PACKAGES =+ "${PN}-ta ${PN}-host"

ALLOW_EMPTY_${PN}-ta = "1"

FILES_${PN} = ""
FILES_${PN}-ta += "/lib/optee_armtz/*"
FILES_${PN}-host += "/usr/bin/*"


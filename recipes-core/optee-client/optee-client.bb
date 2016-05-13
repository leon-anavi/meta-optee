SUMMARY = "OP-TEE client"
LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=69663ab153298557a59c67a60a743e5b"

SRC_URI = "git://github.com/OP-TEE/optee_client;protocol=https;branch=master"
SRCREV = "2.0.0"

S = "${WORKDIR}/git"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}/bin ${D}/include ${D}/lib
    install -m 0755 ${B}/out/export/bin/* ${D}/bin/
    install -m 0644 ${B}/out/export/include/* ${D}/include/
    #TODO: uses SOLIBSDEV
    install -m 0755 ${B}/out/export/lib/* ${D}/lib/
}

PROVIDES += "libteec tee-supplicant"

PACKAGES =+ "libteec tee-supplicant"

ALLOW_EMPTY_${PN} = "1"

FILES_${PN} = ""
FILES_libteec += "/lib/* /include/*"
FILES_tee-supplicant += "/bin/*"


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
    install -d ${D}/${bindir} ${D}/${includedir} ${D}/${base_libdir}

    install -m 0755 ${B}/out/export/bin/* ${D}/${bindir}
    install -m 0644 ${B}/out/export/include/* ${D}/${includedir}

    install -m 0755 ${B}/out/export/lib/* ${D}/${base_libdir}
}


PROVIDES += "libteec tee-supplicant"
PACKAGES =+ "libteec-dev libteec-dbg libteec tee-supplicant-dbg tee-supplicant"

FILES_${PN} = ""

FILES_libteec += "${base_libdir}/lib*so*"
FILES_libteec-dbg += "${base_libdir}/.debug"
FILES_libteec-dev += "${includedir}"

FILES_tee-supplicant += "${bindir}/*"
FILES_tee-supplicant-dbg += "${bindir}/.debug"


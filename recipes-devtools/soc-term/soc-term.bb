SUMMARY = "soc_term native"
LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/soc_term.c;md5=f464caa9737715735f0037b42238331a"

SRC_URI = "git://github.com/linaro-swg/soc_term.git;protocol=https;branch=master"
SRCREV = "7f2da75e9f106bc3e7ed81dd0ff541a49e04dd8c"

S = "${WORKDIR}/git"

# this recipe is native only:
do_configure_class-target() {
    :
}

do_compile_class-target() {
    :
}

# native tool
do_configure_class-native() {
    :
}

do_compile_class-native() {
    oe_runmake
}

do_install_class-native() {
    install -d ${D}
    install -m 0755 ${S}/soc_term ${D}
    install -m 0755 ${S}/soc_term ${STAGING_BINDIR_NATIVE}
}

BBCLASSEXTEND = "native"


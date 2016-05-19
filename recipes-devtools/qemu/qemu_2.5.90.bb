require qemu.inc

QEMU_TARGET = "arm-softmmu"

LIC_FILES_CHKSUM = "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
                    file://COPYING.LIB;endline=24;md5=c04def7ae38850e7d3ef548588159913"

SRC_URI = "git://github.com/qemu/qemu.git;protocol=https;branch=master"
SRCREV = "de1d099a448beb2ec39af4bd9ce4dd6452a18cb5"
S = "${WORKDIR}/git"

DEPENDS = "soc-term-native"

COMPATIBLE_HOST_class-target_mips64 = "null"

do_install_prepend() {
    touch ${WORKDIR}/powerpc_rom.bin
}

do_install_append() {
    rm ${WORKDIR}/powerpc_rom.bin
    # Prevent QA warnings about installed ${localstatedir}/run
    if [ -d ${D}${localstatedir}/run ]; then rmdir ${D}${localstatedir}/run; fi
}


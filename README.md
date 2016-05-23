# meta-optee quick start

Basic integration of OP-TEE in AGL/Yocto  (armv7 virtual machine only)

## Fetch the sources

```bash
repo init -u https://gerrit.automotivelinux.org/gerrit/AGL/AGL-repo
repo sync
git clone https://github.com/iotbzh/meta-optee
```

## Yocto configuration

1. Prepare bitbake environment (do it once)
   ```bash
   source meta-agl/scripts/envsetup.sh qemuarm build-qemu-armv7
   ```

2. Edit your `local.conf` file and add following on top of it:
   ```python
   MACHINE = "qemuarm-virt"     # Note the use of '=' and _not_ '?='
   ```

3. Add the layer to your `bblayer.conf`.

## Build the images

Generate the FS image and optee os:
```bash
bitbake agl-image-minimal
```

## Start QEmu

```bash
../meta-optee/scripts/runqemu-virt qemuarm nographic agl-image-minimal ext4
```

Then press ```c``` in Qemu monitor console to start the session.

Inside the virtual machine terminal, you can launch optee test suite using:

```bash
tee-supplicant &
xtest
```

# Limitations

- Only one virtual machine is supported (cortex-a15),

- ~~At this time, the bios-qemu-tz-arm recipe is depending on the do_rootfs task of agl-image-minimal. Thus, it is not possible to embed it yet in the image, it requires qemu to be able to uses the rootfs file image directly instead of using cpio initramfs mechanism.~~

# External references

- [OP-TEE project](https://github.com/OP-TEE)
- [Linaro wiki](https://wiki.linaro.org/WorkingGroups/Security/OP-TEE)


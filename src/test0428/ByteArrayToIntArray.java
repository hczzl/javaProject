package test0428;

/**
 * @author zhongzhilong
 * @date 2021/4/28
 * @description byte数组和int数组的相互转换
 */
public class ByteArrayToIntArray {
    public static void main(String[] args) {
        byte[] bytes = new byte[]{0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x10, 0x01,};
        int[] arr = byteToInt(bytes);
        System.out.println("byte数组转为int数组：" + arr);
        byte[] tempByte = intToByte(arr);
        System.out.println("int数组转为byte数组：" + tempByte);
    }

    /**
     * byte数组转int数组
     * int 占4个byte字节
     *
     * @param btarr
     * @return
     */
    public static int[] byteToInt(byte[] btarr) {
        if (btarr.length % 4 != 0) {
            return null;
        }
        int[] intarr = new int[btarr.length / 4];

        int i1, i2, i3, i4;
        // j循环int	k循环byte数组
        for (int j = 0, k = 0; j < intarr.length; j++, k += 4) {
            i1 = btarr[k];
            i2 = btarr[k + 1];
            i3 = btarr[k + 2];
            i4 = btarr[k + 3];

            if (i1 < 0) {
                i1 += 256;
            }
            if (i2 < 0) {
                i2 += 256;
            }
            if (i3 < 0) {
                i3 += 256;
            }
            if (i4 < 0) {
                i4 += 256;
            }
            // 保存Int数据类型转换
            intarr[j] = (i1 << 24) + (i2 << 16) + (i3 << 8) + (i4 << 0);

        }
        return intarr;
    }

    /**
     * int 数组转为byte数组
     *
     * @param intarr
     * @return
     */
    public static byte[] intToByte(int[] intarr) {
        // 长度
        int bytelength = intarr.length * 4;
        // 开辟数组
        byte[] bt = new byte[bytelength];
        int curint = 0;
        for (int j = 0, k = 0; j < intarr.length; j++, k += 4) {
            curint = intarr[j];
            // 右移4位，与1作与运算
            bt[k] = (byte) ((curint >> 24) & 0b1111_1111);
            bt[k + 1] = (byte) ((curint >> 16) & 0b1111_1111);
            bt[k + 2] = (byte) ((curint >> 8) & 0b1111_1111);
            bt[k + 3] = (byte) ((curint >> 0) & 0b1111_1111);
        }
        return bt;
    }
}

package cn.exrick.xboot.modules.indoor.mqtt.utils;

import java.util.Arrays;

public class ByteUtil {

    /**
     * @param buf byte[]
     * @return String
     */
    public static String toHexString(byte[] buf) {
        StringBuilder hex = new StringBuilder(buf.length);
        for (int i = 0; i < buf.length; i++) {
            hex.append(toHexString(buf[i]));
        }
        return hex.toString();
    }

    /**
     *
     * @param b byte
     * @return String
     */
    public static String toHexString(byte b) {
        String s = Integer.toHexString(b);
        if (s.length() == 1) {
            s = "0" + s;
        } else if (s.length() > 2) {
            s = s.substring(s.length() - 2);
        }
        return s;
    }

    /**
     *
     * @param b byte
     * @return int
     */
    public static int asUnsignedByte(byte b) {
        return Integer.parseInt(toHexString(b), 16);
    }

    /**
     *
     * @param low   byte
     * @param hight byte
     * @return int
     */
    public static int valueOf2byte(byte low, byte hight) {
        String value = toHexString(hight) + toHexString(low);
        return Integer.parseInt(value, 16);
    }

    /**
     *
     * @param b byte
     * @return String
     */
    public static String toBinaryString(byte b) {
        StringBuilder builder = new StringBuilder(8);
        String bin = Integer.toBinaryString(b);
        builder.append(bin);
        int len = bin.length();
        for (int i = len; i < 8; i++){
            builder.insert(0, "0");
        }
        return builder.toString();
    }

    /**
     *
     *
     * @param b
     * @return
     */
    public static String toBinaryString2(byte b) {
        String hex = toHexString(b);
        int l = hex.length();
        StringBuilder sb = new StringBuilder(l);
        for (int i = 0; i < l; i++) {
            String s = String.valueOf(hex.charAt(i));
            String hex1 = Integer.toBinaryString(Integer.parseInt(s, 16));
            for (int j = hex1.length(); j < 4; j++) {
                sb.append("0");
            }
            sb.append(hex1);
        }
        return sb.toString();
    }

    /**
     *
     * @param b        byte
     * @param startbit int
     * @param len      int
     * @return int
     */
    public static int getBit(byte b, int startbit, int len) {
        String bitStr = toBinaryString(b);
        bitStr = bitStr.substring(8 - startbit - len, 8 - startbit);
        return Integer.parseInt(bitStr, 2);
    }

    /**
     *
     *
     * @param buf byte[]
     * @return byte
     */
    public static byte getCheckCS(byte[] buf, int offset, int len) {
        int b = 0;
        if (buf == null) {
            return 0;
        }
        if (len > buf.length) {
            len = buf.length;
        }
        for (int i = offset; i < offset + len; i++) {
            b = (byte) (b + buf[i]);
        }
        return (byte) b;
    }

    /**
     *
     *
     * @param buf byte[]
     */
    public static void clearbuf(byte[] buf) {
        if (buf == null) {
            return;
        }
        for (int i = 0; i < buf.length; i++) {
            buf[i] = 0;
        }
    }

    /**
     *
     *
     * @param buf1 byte[]
     * @param buf2 byte[]
     * @param len  int
     * @return byte[]
     */
    public static byte[] conj(byte[] buf1, byte[] buf2, int len) {
        if (buf1 == null && buf2 == null){
            return new byte[0];
        }

        if (buf2 == null) {
            return buf1;
        }
        if (buf2.length < len) {
            len = buf2.length;
        }
        int totalLen = len;
        int i;
        byte[] buf;
        if (buf1 == null) {
            buf = Arrays.copyOf(buf2, totalLen);
        } else {
            totalLen = totalLen + buf1.length;
            buf = Arrays.copyOf(buf1, totalLen);
            for (i = buf1.length; i < totalLen; i++) {
                buf[i] = buf2[i - buf1.length];
            }
        }
        return buf;
    }

    /**
     *
     *
     * @param buf int[]
     * @return byte[]
     */
    public static byte[] toBytes(int[] buf) {
        if (buf == null) {
            return new byte[0];
        }
        byte[] b = new byte[buf.length];
        for (int i = 0; i < buf.length; i++) {
            b[i] = (byte) buf[i];
        }
        return b;
    }

    /**
     *
     *
     * @param buf
     * @param start
     * @param len
     * @return
     */
    public static byte[] subBytes(byte[] buf, int start, int len) {
        if (null == buf) {
            return new byte[0];
        }
        if (len < 1) {
            return new byte[0];
        }
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = buf[start + i];
        }
        return bytes;
    }

    /**
     *
     *
     * @param intValue
     * @param normal
     * @return
     */
    public static byte[] integerToBytes(int intValue, boolean normal) {
        byte[] byteValue = new byte[4];
        int shift = 0;

        for (int x = 0; x < 4; x++) {
            shift -= 8;
            if (normal) {
                byteValue[x] = (byte) (intValue >>> shift);
            } else {
                byteValue[3 - x] = (byte) (intValue >>> shift);
            }
        }

        return byteValue;
    }

    /**
     *
     *
     * @param
     */
    public static int bytesToInteger(String value) {
        if (value == null){
            throw new IllegalStateException();
        }
        int i = Integer.parseInt(value, 16);
        byte low = (byte) i;
        byte high = (byte) (i >>> 8);
        int intValue = 0;

        int flag = high & 0x08;
        if (flag == 0) {
            high &= (byte) 0x07;
        }
        else {
            high |= (byte) 0xf8;
            high = (byte) (~high);
            low = (byte) (~low);
        }
        intValue |= (high & 0xff);
        intValue <<= 8;
        intValue |= (low & 0xff);

        if (flag == 8) {
            intValue = -(intValue + 1);
        }
        return intValue;
    }

    /**
     *
     *
     * @param value
     * @param start
     * @param len
     * @return
     */
    public static int getHexbit(byte value, int start, int len) {
        String hex = toHexString(value);
        hex = hex.substring(0, 1);
        return Integer.parseInt(hex, 16);
    }

    /**
     *
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean checkByteArray(byte[] array1, byte[] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int getRssi(byte hi, byte low) {
        int i = valueOf2byte(low, hi);
        int j = 0;
        if (i > 0x8000) {
            j = ((i ^ 0xFFFF) + 1) / (-10);
        } else {
            j = i / 10;
        }
        return j;
    }

    /**
     * 十六进制数组转换字符
     *
     * @param src byte[]
     * @return String
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    // 字符串转Ascii
    public static String stringToAscii(String value) {

        char[] chars = value.toCharArray();
        int len = chars.length;
        StringBuilder sbu = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            if (i != len - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * 十进制转十六进制
     */
    public static String decimalToHexadecimal(int number) {

        int i = 0;
        char[] s = new char[100];
        StringBuilder result = new StringBuilder(s.length);
        if (number == 0) {
            result.append("0");
        } else {
            while (number != 0) {
                int t = number % 16;
                if (t >= 0 && t < 10) {
                    s[i] = (char) (t + '0');
                    i++;
                } else {
                    s[i] = (char) (t + 'A' - 10);
                    i++;
                }
                number = number / 16;
            }

            for (int j = i - 1; j >= 0; j--) {
                result.append(s[j]);
            }
        }
        return result.toString();
    }

    /**
     * @param
     * @return
     */
    public static String reverseHex(final String hex) {
        final char[] charArray = hex.toCharArray();
        final int length = charArray.length;
        final int times = length / 2;
        for (int c1i = 0; c1i < times; c1i += 2) {
            final int c2i = c1i + 1;
            final char c1 = charArray[c1i];
            final char c2 = charArray[c2i];
            final int c3i = length - c1i - 2;
            final int c4i = length - c1i - 1;
            charArray[c1i] = charArray[c3i];
            charArray[c2i] = charArray[c4i];
            charArray[c3i] = c1;
            charArray[c4i] = c2;
        }
        return new String(charArray);
    }


    public static final char TABLE1021[] = { /* CRC1021余式�? */
            0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7, 0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c,
            0xd1ad, 0xe1ce, 0xf1ef, 0x1231, 0x0210, 0x3273, 0x2252, 0x52b5, 0x4294, 0x72f7, 0x62d6, 0x9339, 0x8318,
            0xb37b, 0xa35a, 0xd3bd, 0xc39c, 0xf3ff, 0xe3de, 0x2462, 0x3443, 0x0420, 0x1401, 0x64e6, 0x74c7, 0x44a4,
            0x5485, 0xa56a, 0xb54b, 0x8528, 0x9509, 0xe5ee, 0xf5cf, 0xc5ac, 0xd58d, 0x3653, 0x2672, 0x1611, 0x0630,
            0x76d7, 0x66f6, 0x5695, 0x46b4, 0xb75b, 0xa77a, 0x9719, 0x8738, 0xf7df, 0xe7fe, 0xd79d, 0xc7bc, 0x48c4,
            0x58e5, 0x6886, 0x78a7, 0x0840, 0x1861, 0x2802, 0x3823, 0xc9cc, 0xd9ed, 0xe98e, 0xf9af, 0x8948, 0x9969,
            0xa90a, 0xb92b, 0x5af5, 0x4ad4, 0x7ab7, 0x6a96, 0x1a71, 0x0a50, 0x3a33, 0x2a12, 0xdbfd, 0xcbdc, 0xfbbf,
            0xeb9e, 0x9b79, 0x8b58, 0xbb3b, 0xab1a, 0x6ca6, 0x7c87, 0x4ce4, 0x5cc5, 0x2c22, 0x3c03, 0x0c60, 0x1c41,
            0xedae,

            0xfd8f, 0xcdec, 0xddcd, 0xad2a, 0xbd0b, 0x8d68, 0x9d49, 0x7e97, 0x6eb6, 0x5ed5, 0x4ef4, 0x3e13, 0x2e32,
            0x1e51, 0x0e70, 0xff9f, 0xefbe, 0xdfdd, 0xcffc, 0xbf1b, 0xaf3a, 0x9f59, 0x8f78, 0x9188, 0x81a9, 0xb1ca,
            0xa1eb, 0xd10c, 0xc12d, 0xf14e, 0xe16f, 0x1080, 0x00a1, 0x30c2, 0x20e3, 0x5004, 0x4025, 0x7046, 0x6067,
            0x83b9, 0x9398, 0xa3fb, 0xb3da, 0xc33d, 0xd31c, 0xe37f, 0xf35e, 0x02b1, 0x1290, 0x22f3, 0x32d2, 0x4235,
            0x5214, 0x6277, 0x7256, 0xb5ea, 0xa5cb, 0x95a8, 0x8589, 0xf56e, 0xe54f, 0xd52c, 0xc50d, 0x34e2, 0x24c3,
            0x14a0, 0x0481, 0x7466, 0x6447, 0x5424, 0x4405, 0xa7db, 0xb7fa, 0x8799, 0x97b8, 0xe75f, 0xf77e, 0xc71d,
            0xd73c, 0x26d3, 0x36f2, 0x0691, 0x16b0, 0x6657, 0x7676, 0x4615, 0x5634, 0xd94c, 0xc96d, 0xf90e, 0xe92f,
            0x99c8, 0x89e9, 0xb98a, 0xa9ab, 0x5844, 0x4865, 0x7806, 0x6827, 0x18c0, 0x08e1, 0x3882, 0x28a3, 0xcb7d,
            0xdb5c, 0xeb3f, 0xfb1e, 0x8bf9, 0x9bd8, 0xabbb, 0xbb9a, 0x4a75, 0x5a54, 0x6a37, 0x7a16, 0x0af1, 0x1ad0,
            0x2ab3, 0x3a92, 0xfd2e, 0xed0f, 0xdd6c, 0xcd4d, 0xbdaa, 0xad8b, 0x9de8, 0x8dc9, 0x7c26, 0x6c07, 0x5c64,
            0x4c45, 0x3ca2, 0x2c83, 0x1ce0, 0x0cc1, 0xef1f, 0xff3e, 0xcf5d, 0xdf7c, 0xaf9b, 0xbfba, 0x8fd9, 0x9ff8,
            0x6e17, 0x7e36, 0x4e55, 0x5e74, 0x2e93, 0x3eb2, 0x0ed1, 0x1ef0 };

    public static int crc16_ccitt(byte[] buf, int len) {
        int counter;
        int crc = 0;
        for (counter = 0; counter < len; counter++) {
            crc = (crc << 8) ^ TABLE1021[((crc >> 8) ^ buf[counter]) & 0x00FF];
        }
        return crc & 0xFFFF;
    }

    public static String crcSend(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().replace("5e", "5e5d").replace("7e", "5e7d");
    }

    /**
     * CRC16 校验
     * 低位在左 高位在右
     *
     * @param crc16Data
     * @return
     */
    public static String crc(String crc16Data) {
        byte[] hexByte = hexStringToBytes(crc16Data);
        int crc16_ccitt = crc16_ccitt(hexByte, hexByte.length);

        int big = (crc16_ccitt & 0xFF00) >> 8;
        int little = crc16_ccitt & 0xFF;

        return String.format("%02x", little) + String.format("%02x", big);
    }

    public static String crcAlgo(String crc16Data) {
        byte[] hexByte = hexStringToBytes(crc16Data);
        int crc16_ccitt = crc16_ccitt(hexByte, hexByte.length);

        int big = (crc16_ccitt & 0xFF00) >> 8;
        int little = crc16_ccitt & 0xFF;

        String res = String.format("%02x", little) + String.format("%02x", big);

        return crcSend(hexStringToBytes(res));
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return new byte[0];
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte[] abt = asc.getBytes();
        if (len >= 2) {
            len = len / 2;
        }

        byte[] bbt = new byte[len];
        int j;
        int k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }


    // 指令遇上5e5d和5e7e转义
    public static String crcRecived(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().replaceAll("5e5d", "5e").replaceAll("5e7d", "7e");
    }

}

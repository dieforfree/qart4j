package free6om.research.qart4j;

/**
 *
 * Created by free6om on 7/20/15.
 */
public class Pixel {
    public enum PixelRole {
        UNKNOWN, //not used
        POSITION, //position pattern
        ALIGNMENT,
        TIMING,
        FORMAT,
        VERSION_PATTERN,
        UNUSED,
        DATA,
        CHECK,
        EXTRA

    }



    public static final Pixel BLACK = new Pixel(1);
    public static final Pixel INVERT = new Pixel(2);

    private int data;

    public Pixel(int value) {
        data = value;
    }

    public Pixel(PixelRole role) {
        this.data = role.ordinal() << 2;
    }

    public int getOffset() {
        return data >> 6;
    }

    public void setOffset(int offset) {
        data = ((offset << 6) | (data & 0x03F));
    }

    public PixelRole getPixelRole() {
        int ordinal = (this.data >> 2) & 0x0F;
        if(ordinal < PixelRole.UNKNOWN.ordinal() || ordinal > PixelRole.EXTRA.ordinal()){
            return null;
        }

        return PixelRole.values()[ordinal];
    }

    public int getPixelValue() {
        return this.data & 0x03;
    }
    public void setPixelValue(int value) {
        this.data = ((this.data >> 2 << 2) | (value&0x03));
    }
    public void orPixelValue(int value) {
        this.data |= (value & 0x03);
    }
    public void xorPixelValue(int value) {
        this.data ^= (value & 0x03);
    }

}

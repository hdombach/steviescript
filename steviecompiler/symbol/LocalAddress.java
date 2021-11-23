package steviecompiler.symbol;

public class LocalAddress {
    int frame;
    int offset;

    LocalAddress(int frame, int offset) {
        this.frame = frame;
        this.offset = offset;
    }
    LocalAddress increased() {
        this.frame += 1;
        return this;
    }
}

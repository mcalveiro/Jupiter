package vsim.riscv.isa.instructions.btype;

import vsim.Globals;
import vsim.riscv.hardware.Register;
import vsim.riscv.isa.instructions.Code;
import vsim.riscv.isa.instructions.Format;
import vsim.riscv.isa.instructions.SimCode;


public abstract class Branch extends SimCode {

    public abstract boolean comparison(int x, int y);

    @Override
    public void execute(Code code) {
        Register rs1 = Globals.regfile.getRegister(code.getRs1());
        Register rs2 = Globals.regfile.getRegister(code.getRs2());

        if (this.comparison(rs1.getValue(), rs2.getValue())) {
            Register pc = Globals.regfile.getProgramCounter();
            pc.setValue(pc.getValue() + code.getImm(Format.B));
        }

    }

}
/*
Copyright (C) 2018-2019 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package jupiter.riscv.instructions.rtype;

import jupiter.exc.SimulationException;
import jupiter.riscv.instructions.Format;
import jupiter.riscv.instructions.Instruction;
import jupiter.riscv.instructions.InstructionField;
import jupiter.riscv.instructions.MachineCode;
import jupiter.sim.State;
import jupiter.utils.Data;


/** RISC-V fsgnjn.s (Floating-point Sign Inject-Negate, Single-Precision) instruction. */
public final class FSGNJNS extends Instruction {

  /** Creates a new fmv.x.w instruction. */
  public FSGNJNS() {
    super(Format.R, "fsgnjn.s", 0b1010011, 0b001, 0b0010000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    float rs1 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    float rs2 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS2));
    int ax = Float.floatToIntBits(rs1) & (Data.EXPONENT_MASK | Data.FRACTION_MASK);
    int bx = ~Float.floatToIntBits(rs2) & Data.SIGN_MASK;
    state.fregfile().setRegister(code.get(InstructionField.RD), Float.intBitsToFloat(ax | bx));
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    return String.format("fsgnjn.s f%d, f%d, f%d", rd, rs1, rs2);
  }

}

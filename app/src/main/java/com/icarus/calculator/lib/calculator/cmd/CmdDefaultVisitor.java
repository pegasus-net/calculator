package com.icarus.calculator.lib.calculator.cmd;

//import org.xing.calc.cmd.grammer.cmdBaseVisitor;
//import org.xing.calc.cmd.grammer.cmdParser;

//import com.zss.intelligentcalculator.util.cmd.grammer.cmdBaseVisitor;


import com.icarus.calculator.lib.calculator.cmd.grammer.cmdBaseVisitor;
import com.icarus.calculator.lib.calculator.cmd.grammer.cmdParser;

public class CmdDefaultVisitor extends cmdBaseVisitor<Integer> {
	@Override
    public Integer visitCommand(cmdParser.CommandContext ctx) {
		if(ctx.qingping() != null) {
			return 1;
		}else if(ctx.goback() != null) {
			return 2;
		}else if(ctx.help() != null) {
			return 3;
		}else if(ctx.update() != null) {
			return 4;
		}else if(ctx.theme() != null) {
			return 5;
		}else if(ctx.engine() != null) {
			return 6;
		}else if(ctx.close() != null) {
			return 7;
		}else if(ctx.stop() != null) {
			return 8;
		}
		
		return 0;
	}
}

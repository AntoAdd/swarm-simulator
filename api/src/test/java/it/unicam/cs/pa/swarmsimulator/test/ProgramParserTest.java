package it.unicam.cs.pa.swarmsimulator.test;

import it.unicam.cs.pa.swarmsimulator.io.FollowMeParser;
import it.unicam.cs.pa.swarmsimulator.io.FollowMeParserException;
import it.unicam.cs.pa.swarmsimulator.io.FollowMeParserHandler;
import it.unicam.cs.pa.swarmsimulator.io.ProgramParserHandler;
import it.unicam.cs.pa.swarmsimulator.model.commands.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramParserTest {
    private final FollowMeParserHandler handler = new ProgramParserHandler();
    private final FollowMeParser parser = new FollowMeParser(handler);

    @Test
    public void simpleProgramShouldBeCorrectlyParsed() throws FollowMeParserException, IOException {
        File file = new File("src/test/resources/program.txt");
        parser.parseRobotProgram(file);
        Optional<List<RobotCommand>> result = handler.getParsedProgram();

        assertTrue(result.isPresent());

        List<RobotCommand> program = result.get();

        assertEquals(3, program.size());
        assertInstanceOf(Move.class, program.get(0));
        assertInstanceOf(Continue.class, program.get(1));
        assertInstanceOf(Signal.class, program.get(2));
    }

    @Test
    public void complexProgramShouldBeCorrectlyParsed() throws FollowMeParserException, IOException {
        File file = new File("src/test/resources/complexProgram.txt");
        parser.parseRobotProgram(file);
        Optional<List<RobotCommand>> result = handler.getParsedProgram();

        assertTrue(result.isPresent());

        List<RobotCommand> program = result.get();

        assertEquals(5, program.size());
        assertInstanceOf(Move.class, program.get(0));
        assertInstanceOf(Continue.class, program.get(1));
        assertInstanceOf(Repeat.class, program.get(2));
        assertInstanceOf(Signal.class, program.get(3));
        assertInstanceOf(Stop.class, program.get(4));

        List<RobotCommand> subcommands = program.get(2).getSubcommandsList();

        assertEquals(5, subcommands.size());
        assertInstanceOf(Move.class, subcommands.get(0));
        assertInstanceOf(Continue.class, subcommands.get(1));
        assertInstanceOf(Move.class, subcommands.get(2));
        assertInstanceOf(Continue.class, subcommands.get(3));
        assertInstanceOf(Done.class, subcommands.get(4));
    }
}

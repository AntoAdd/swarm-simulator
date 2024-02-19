package it.unicam.cs.pa.swarmsimulator.io;

import it.unicam.cs.pa.swarmsimulator.model.PlainLocation;
import it.unicam.cs.pa.swarmsimulator.model.commands.*;
import it.unicam.cs.pa.swarmsimulator.model.commands.RobotCommand;
import it.unicam.cs.pa.swarmsimulator.model.robot.SignalingCondition;
import it.unicam.cs.pa.swarmsimulator.model.robot.Speed;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgramParserHandler implements FollowMeParserHandler {
    private boolean parsing = false;
    private final List<RobotCommand> program = new ArrayList<>();
    private List<RobotCommand> nestingCommandList = new ArrayList<>();


    @Override
    public Optional<List<RobotCommand>> getParsedProgram() {
        if (parsing)
            return Optional.empty();
        else
            return Optional.of(program);
    }

    @Override
    public void parsingStarted() {
        parsing = true;
    }

    @Override
    public void parsingDone() {
        parsing = false;
    }

    @Override
    public void moveCommand(double[] args) {
        Move move = new Move(new PlainLocation(args[0], args[1]), new Speed(args[2]));
        if (!nestingCommandList.isEmpty())
            addToParentCommand(move, nestingCommandList.getLast());
        else
            program.add(move);
    }

    private void addToParentCommand(RobotCommand command, RobotCommand parentCommand) {
        switch (parentCommand){
            case Repeat repeat -> repeat.addSubcommand(command);
            case Until until -> until.addSubcommand(command);
            case Forever forever -> forever.addSubcommand(command);
            default -> throw new IllegalStateException("Unexpected value: " + command);
        }
    }

    @Override
    public void moveRandomCommand(double[] args) {
        MoveRandom moveRandom = new MoveRandom(
            new PlainLocation(args[0], args[2]),
            new PlainLocation(args[1], args[3]),
            new Speed(args[4])
        );
        if (!nestingCommandList.isEmpty())
            addToParentCommand(moveRandom, nestingCommandList.getLast());
        else
            program.add(moveRandom);
    }

    @Override
    public void signalCommand(String label) {
        Signal signal = new Signal(new SignalingCondition(label));
        if (!nestingCommandList.isEmpty())
            addToParentCommand(signal, nestingCommandList.getLast());
        else
            program.add(signal);
    }

    @Override
    public void unsignalCommand(String label) {
        Unsignal unsignal = new Unsignal(new SignalingCondition(label));
        if (!nestingCommandList.isEmpty())
            addToParentCommand(unsignal, nestingCommandList.getLast());
        else
            program.add(unsignal);
    }

    @Override
    public void followCommand(String label, double[] args) {
        Follow follow = new Follow(new SignalingCondition(label), args[0], new Speed(args[1]));
        if (!nestingCommandList.isEmpty())
            addToParentCommand(follow, nestingCommandList.getLast());
        else
            program.add(follow);
    }

    @Override
    public void stopCommand() {
        Stop stop = new Stop();
        if (!nestingCommandList.isEmpty())
            addToParentCommand(stop, nestingCommandList.getLast());
        else
            program.add(stop);
    }

    @Override
    public void continueCommand(int s) {
        Continue continueCommand = new Continue(s);
        if (!nestingCommandList.isEmpty())
            addToParentCommand(continueCommand, nestingCommandList.getLast());
        else
            program.add(continueCommand);
    }

    @Override
    public void repeatCommandStart(int n) {
        Repeat repeat = new Repeat(n);
        nestingCommandList.add(repeat);
    }

    @Override
    public void untilCommandStart(String label) {
        Until until = new Until(new SignalingCondition(label));
        nestingCommandList.add(until);
    }

    @Override
    public void doForeverStart() {
        Forever forever = new Forever();
        nestingCommandList.add(forever);
    }

    @Override
    public void doneCommand() {
        Done done = new Done(nestingCommandList.getLast());
        addToParentCommand(done, nestingCommandList.getLast());
        if (nestingCommandList.size() > 1) {
            addToParentCommand(nestingCommandList.getLast(), nestingCommandList.get(nestingCommandList.size() - 2));
            nestingCommandList.removeLast();
        } else {
            program.add(nestingCommandList.getLast());
            nestingCommandList.removeLast();
        }
    }
}

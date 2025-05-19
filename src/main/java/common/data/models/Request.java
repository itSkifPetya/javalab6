package common.data.models;

import common.domain.command.Command;

import java.io.Serializable;

public class Request implements Serializable {
    private final Command command;
    private final String[] args;

    public Request(Command command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public Command getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}

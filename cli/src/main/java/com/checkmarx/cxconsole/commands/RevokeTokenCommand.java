package com.checkmarx.cxconsole.commands;

import com.checkmarx.cxconsole.commands.constants.Commands;
import com.checkmarx.cxconsole.commands.exceptions.CLICommandException;
import com.checkmarx.cxconsole.commands.exceptions.CLICommandParameterValidatorException;
import com.checkmarx.cxconsole.commands.job.CxRevokeTokenJob;
import com.checkmarx.cxconsole.commands.utils.CommandParametersValidator;
import com.checkmarx.parameters.CLIScanParametersSingleton;
import org.apache.commons.cli.Options;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by nirli on 31/10/2017.
 */
class RevokeTokenCommand extends CLICommand {

    RevokeTokenCommand(CLIScanParametersSingleton params) {
        super(params);
        this.commandName = Commands.REVOKE_TOKEN.value();
    }

    @Override
    protected int executeCommand() throws CLICommandException {
        CxRevokeTokenJob job = new CxRevokeTokenJob(params);

        Future<Integer> future = executor.submit(job);
        try {
            if (timeoutInSeconds != null) {
                exitCode = future.get(timeoutInSeconds, TimeUnit.SECONDS);
            } else {
                exitCode = future.get();
            }
        } catch (Exception e) {
            log.error("Error executing RevokeToken command, due to: " + e.getMessage());
            throw new CLICommandException("Error executing RevokeToken command, due to: " + e.getMessage());
        }
        return exitCode;
    }

    @Override
    public void checkParameters() throws CLICommandParameterValidatorException {
        CommandParametersValidator.validateRevokeTokenParams(params);
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getUsageExamples() {
        return "RevokeToken -CxToken 1241513513tsfrg42 -CxServer http://localhost -v";
    }

    @Override
    public void printHelp() {
        String helpHeader = "\nThe \"RevokeToken\" command allows to discard existing token.";
        String helpFooter = "\nUsage example: " + getUsageExamples() + "\n\n(c) 2017 CheckMarx.com LTD, All Rights Reserved\n";
        helpFormatter.printHelp(120, getCommandName(), helpHeader, (Options) params.getCliMandatoryParameters().getRevokeTokenMandatoryParamsOptionGroup().getOptions(), helpFooter, true);
    }
}
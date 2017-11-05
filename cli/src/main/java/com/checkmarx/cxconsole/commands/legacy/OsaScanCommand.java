//package com.checkmarx.cxconsole.commands;
//
//import com.checkmarx.cxconsole.commands.constants.Commands;
//import com.checkmarx.cxconsole.commands.exceptions.CommandLineArgumentException;
//import com.checkmarx.cxconsole.commands.constants.LocationType;
//import org.apache.commons.cli.HelpFormatter;
//import org.apache.commons.cli.Options;
//
//public class OsaScanCommand extends ScanCommand {
//
//    private String osaCommand;
//    private boolean isAsyncOsaScan;
//
//    OsaScanCommand(boolean isAsyncOsaScan) {
//        super(false);
//        this.isAsyncOsaScan = isAsyncOsaScan;
//        if (isAsyncOsaScan) {
//            osaCommand = Commands.ASYNC_OSA_SCAN.value();
//        } else {
//            osaCommand = Commands.OSASCAN.value();
//        }
//    }
////
////    @Override
////    public String getCommandName() {
////        return osaCommand;
////    }
////
////    @Override
////    public String getUsageExamples() {
////        return "\n\nrunCxConsole.cmd OsaScan -v -Projectname SP\\Cx\\Engine\\AST -CxServer http://localhost -cxuser admin -cxpassword admin -osaLocationPath C:\\cx  -OsaFilesExclude *.class OsaPathExclude src,temp  \n"
////                + "runCxConsole.cmd  OsaScan -v -projectname SP\\Cx\\Engine\\AST -cxserver http://localhost -cxuser admin -cxpassword admin -locationtype folder -locationurl http://vsts2003:8080 -locationuser dm\\matys -locationpassword XYZ  -OsaReportPDF -\n"
////                + "runCxConsole.cmd  OsaScan -v -projectname SP\\Cx\\Engine\\AST -cxserver http://localhost -cxuser admin -cxpassword admin -locationtype shared -locationpath '\\storage\\path1;\\storage\\path2' -locationuser dm\\matys -locationpassword XYZ  -OsaReportPDF -OsaReportHTML -log a.log\n \n"
////                + "runCxConsole.cmd  OsaScan -v -Projectname CxServer\\SP\\Company\\my project -CxServer http://localhost -cxuser admin -cxpassword admin -locationtype folder -locationpath C:\\Users\\some_project -OsaFilesExclude *.bat -OsaReportPDF";
////    }
//
//
//
////    @Override
////    public void checkParameters() throws CommandLineArgumentException {
////        scParams.getCliSastParameters().setOsaEnabled(false);
////        super.checkParameters();
////        if (scParams.getCliOsaParameters().getOsaLocationPath() == null && (scParams.getCliSharedParameters().getLocationType() != LocationType.folder && scParams.getCliSharedParameters().getLocationType() != LocationType.shared)) {
////            throw new CommandLineArgumentException("For OSA Scan (" + Commands.OSASCAN.value() + "), provide  " + PARAM_OSA_LOCATION_PATH.getOpt() + "  or " + PARAM_LOCATION_TYPE.getOpt() + " ( values: folder/shared)");
////        }
////        if (isAsyncOsaScan && (scParams.getCliOsaParameters().getOsaReportHTML() != null || scParams.getCliOsaParameters().getOsaReportPDF() != null || scParams.getCliOsaParameters().getOsaJson() != null)) {
////            throw new CommandLineArgumentException("Asynchronous run does not allow report creation. Please remove the report parameters and run again");
////        }
////        if (isAsyncOsaScan && (scParams.getCliOsaParameters().getOsaHighThresholdValue() != Integer.MAX_VALUE || scParams.getCliOsaParameters().getOsaMediumThresholdValue() != Integer.MAX_VALUE || scParams.getCliOsaParameters().getOsaLowThresholdValue() != Integer.MAX_VALUE)) {
////            throw new CommandLineArgumentException("Asynchronous run does not support threshold. Please remove the threshold parameters and run again");
////        }
////    }
//
////    private Options getOsaOptionsOnly(Options all) {
////        Options osaOnly = new Options();
////        osaOnly.addOption(all.getOption(PARAM_OSA_LOCATION_PATH.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_LOCATION_TYPE.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_LOCATION_PATH.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_LOG_FILE.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_CONFIG_FILE.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_EXCLUDE_FILES.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_INCLUDE_FILES.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_EXCLUDE_FOLDERS.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_HTML_FILE.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_PDF_FILE.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_JSON.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_USE_SSO.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_VERBOSE.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_LOW_THRESHOLD.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_MEDIUM_THRESHOLD.getOpt()));
////        osaOnly.addOption(all.getOption(PARAM_OSA_HIGH_THRESHOLD.getOpt()));
////
////        return osaOnly;
////    }
//}
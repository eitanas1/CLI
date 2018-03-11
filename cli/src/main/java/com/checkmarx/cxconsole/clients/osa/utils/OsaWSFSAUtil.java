package com.checkmarx.cxconsole.clients.osa.utils;

import com.checkmarx.cxconsole.clients.osa.dto.CreateOSAScanRequest;
import com.checkmarx.cxconsole.parameters.CLIOSAParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.whitesource.fs.ComponentScan;

import java.util.Objects;
import java.util.Properties;

import static com.checkmarx.cxconsole.clients.osa.utils.OsaWSFSAUtil.StringType.*;

/**
 * Created by nirli on 06/02/2018.
 */
public class OsaWSFSAUtil {

    private OsaWSFSAUtil() {
        throw new IllegalStateException("Utility class");
    }

    enum StringType {BASE_DIRECTORIES, OSA_FOLDER_EXCLUDE, OSA_INCLUDE_FILES, OSA_EXCLUDE_FILES, OSA_EXTRACTABLE_FILES}

    private static Logger log = Logger.getLogger(OsaWSFSAUtil.class);

    public static String composeProjectOSASummaryLink(String url, long projectId) {
        return String.format("%s/CxWebClient/SPA/#/viewer/project/%s", url, projectId);
    }

    private static Properties generateOsaScanProperties(String[] osaLocationPath, CLIOSAParameters cliosaParameters) {
        Properties ret = new Properties();
        String osaDirectoriesToAnalyze = stringArrayToString(osaLocationPath, BASE_DIRECTORIES);
        ret.put("d", osaDirectoriesToAnalyze);

        String osaFolderExcludeString = "";
        if (cliosaParameters.isHasOsaExcludedFoldersParam()) {
            osaFolderExcludeString = stringArrayToString(cliosaParameters.getOsaExcludedFolders(), OSA_FOLDER_EXCLUDE);
        }

        String osaFilesExcludesString = "";
        if (cliosaParameters.isHasOsaExcludedFilesParam()) {
            osaFilesExcludesString = stringArrayToString(cliosaParameters.getOsaExcludedFiles(), OSA_EXCLUDE_FILES);
        }

        String osaFilesIncludesString = stringArrayToString(cliosaParameters.getOsaIncludedFiles(), OSA_INCLUDE_FILES);
        ret.put("includes", osaFilesIncludesString);

        String osaExtractableIncludesString = stringArrayToString(cliosaParameters.getOsaExtractableIncludeFiles(), OSA_EXTRACTABLE_FILES);
        ret.put("archiveIncludes", osaExtractableIncludesString);


        StringBuilder osaExcludes = new StringBuilder();
        if (!osaFolderExcludeString.isEmpty()) {
            osaExcludes.append(osaFolderExcludeString).append(" ");
        }
        if (!osaFilesExcludesString.isEmpty()) {
            osaExcludes.append(osaFilesExcludesString);
        }

        if (!osaExcludes.toString().isEmpty()) {
            ret.put("excludes", osaExcludes.toString().trim());
        }
        ret.put("archiveExtractionDepth", cliosaParameters.getOsaScanDepth());
        if (cliosaParameters.isExecuteNpmAndBower()) {
            ret.put("npm.runPreStep", "true");
            ret.put("bower.runPreStep", "true");
        }

        return ret;
    }

    private static String stringArrayToString(String[] strArr, StringType stringType) {
        StringBuilder builder = new StringBuilder();
        if (stringType.equals(StringType.BASE_DIRECTORIES)) {
            if (strArr.length > 1) {
                for (String s : strArr) {
                    builder.append(s.trim()).append(",");
                }
            } else {
                builder.append(strArr[0].trim());
            }
        }

        if (stringType.equals(StringType.OSA_FOLDER_EXCLUDE) && !Objects.equals(strArr[0], "")) {
            for (String s : strArr) {
                builder.append("**/").append(s.trim()).append("/**");
            }
        }

        if ((stringType.equals(StringType.OSA_EXCLUDE_FILES) || stringType.equals(StringType.OSA_EXTRACTABLE_FILES) || stringType.equals(StringType.OSA_INCLUDE_FILES)) && !Objects.equals(strArr[0], "")) {
            if (Objects.equals(strArr[0], "**/**")) {
                return "**/**";
            }

            for (String s : strArr) {
                if (s.startsWith("*.")) {
                    builder.append("**/").append(s.trim()).append(" ");
                } else {
                    builder.append("**/*").append(s.trim()).append(" ");
                }
            }
        }

        return builder.toString().trim();
    }

    public static CreateOSAScanRequest createOsaScanRequest(long projectId, String[] osaLocationPath, CLIOSAParameters cliosaParametersr) {
        Properties scannerProperties = generateOsaScanProperties(osaLocationPath, cliosaParametersr);
        log.info("Generated FSA properties for analysis");

        ComponentScan componentScan = new ComponentScan(scannerProperties);
        log.info("Starting FSA component scan");
        String osaDependenciesJson = componentScan.scan();
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.trace("Scanner properties: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scannerProperties));
            log.trace("List of files sent to WhiteSource: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(osaDependenciesJson));
        } catch (JsonProcessingException e) {
            log.error("Can't write list of files sent to WS " + e.getMessage());
        }

        return new CreateOSAScanRequest(projectId, osaDependenciesJson);
    }

}
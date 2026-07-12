package com.onyx.android.sdk.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ShellUtils {
    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    public static final String COMMAND_NEW_LINE = "\r\n";

    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, true, true);
    }

    public static CommandResult execCommand(boolean isRoot, String... commands) {
        return execCommand(commands, isRoot, true, true);
    }

    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null ? null : commands.toArray(new String[0]),
                isRoot, true, true);
    }

    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true, true);
    }

    public static CommandResult execCommand(
            String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg, true);
    }

    public static CommandResult execCommand(
            List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(commands == null ? null : commands.toArray(new String[0]),
                isRoot, isNeedResultMsg, true);
    }

    public static CommandResult execCommand(
            String[] commands, boolean isRoot,
            boolean isNeedResultMsg, boolean isNeedErrorMsg) {
        if (commands == null || commands.length == 0) {
            return new CommandResult(-1, null, null);
        }
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            CompletableFuture<String> stdout = readAsync(process.getInputStream(), isNeedResultMsg);
            CompletableFuture<String> stderr = readAsync(process.getErrorStream(), isNeedErrorMsg);
            try (DataOutputStream input = new DataOutputStream(process.getOutputStream())) {
                for (String command : commands) {
                    if (command == null) continue;
                    input.write(command.getBytes(StandardCharsets.UTF_8));
                    if (!command.endsWith(COMMAND_LINE_END)) {
                        input.writeBytes(COMMAND_LINE_END);
                    }
                    input.flush();
                }
                input.writeBytes(COMMAND_EXIT);
                input.flush();
            }
            int result = process.waitFor();
            return new CommandResult(result, stdout.get(), stderr.get());
        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommandResult(-1, null, exception.getMessage());
        } finally {
            if (process != null) a(process);
        }
    }

    private static CompletableFuture<String> readAsync(InputStream stream, boolean capture) {
        return CompletableFuture.supplyAsync(() -> {
            StringBuilder output = capture ? new StringBuilder() : null;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (capture) output.append(line).append(COMMAND_NEW_LINE);
                }
            } catch (Exception exception) {
                if (capture) output.append(exception.getMessage());
            }
            return capture ? output.toString() : null;
        });
    }

    private static void a(Process process) {
        try {
            process.exitValue();
        } catch (IllegalThreadStateException exception) {
            process.destroy();
        }
    }

    public static class CommandResult {
        public int result;
        public String successMsg;
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }

        public boolean isSuccessful() {
            return result == 0;
        }
    }
}

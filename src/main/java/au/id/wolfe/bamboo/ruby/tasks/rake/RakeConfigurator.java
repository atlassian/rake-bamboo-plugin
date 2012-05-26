package au.id.wolfe.bamboo.ruby.tasks.rake;

import au.id.wolfe.bamboo.ruby.common.AbstractRubyTaskConfigurator;
import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.ww2.actions.build.admin.create.UIConfigSupport;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Rake configurator which acts as a binding to the task UI in bamboo.
 */
public class RakeConfigurator extends AbstractRubyTaskConfigurator {

    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull ActionParametersMap params, @Nullable TaskDefinition previousTaskDefinition) {

        final Map<String, String> config = generateTaskConfigMap(params, previousTaskDefinition);

        config.put("ruby", params.getString("ruby"));
        config.put("rakefile", params.getString("rakefile"));
        config.put("rakelibdir", params.getString("rakelibdir"));
        config.put("targets", params.getString("targets"));
        config.put("bundleexec", params.getString("bundleexec"));

        config.put("verbose", params.getString("verbose"));
        config.put("trace", params.getString("trace"));

        return config;
    }

    @Override
    public void populateContextForCreate(@NotNull Map<String, Object> context) {

        log.debug("populateContextForCreate");

        context.put("ruby", "");
        context.put("rakefile", "");
        context.put("rakelibdir", "");
        context.put("targets", "");
        context.put("bundleexec", "");

        context.put("verbose", "");
        context.put("trace", "");

        context.put(MODE, CREATE_MODE);
        context.put(CTX_UI_CONFIG_BEAN, uiConfigBean);  // NOTE: This is not normally necessary and will be fixed in 3.3.3
    }

    @Override
    public void populateContextForEdit(@NotNull Map<String, Object> context, @NotNull TaskDefinition taskDefinition) {

        log.debug("populateContextForEdit");

        context.put("ruby", taskDefinition.getConfiguration().get("ruby"));
        context.put("rakefile", taskDefinition.getConfiguration().get("rakefile"));
        context.put("rakelibdir", taskDefinition.getConfiguration().get("rakelibdir"));
        context.put("bundleexec", taskDefinition.getConfiguration().get("bundleexec"));

        context.put("verbose", taskDefinition.getConfiguration().get("verbose"));
        context.put("trace", taskDefinition.getConfiguration().get("trace"));

        context.put("targets", taskDefinition.getConfiguration().get("targets"));

        context.put(MODE, EDIT_MODE);
        context.put(CTX_UI_CONFIG_BEAN, uiConfigBean);  // NOTE: This is not normally necessary and will be fixed in 3.3.3
    }

    @Override
    public void populateContextForView(@NotNull Map<String, Object> context, @NotNull TaskDefinition taskDefinition) {

        log.debug("populateContextForView");

        context.put("ruby", taskDefinition.getConfiguration().get("ruby"));
        context.put("rakefile", taskDefinition.getConfiguration().get("rakefile"));
        context.put("rakelibdir", taskDefinition.getConfiguration().get("rakelibdir"));
        context.put("targets", taskDefinition.getConfiguration().get("targets"));
        context.put("bundleexec", taskDefinition.getConfiguration().get("bundleexec"));

        context.put("verbose", taskDefinition.getConfiguration().get("verbose"));
        context.put("trace", taskDefinition.getConfiguration().get("trace"));

    }

    @Override
    public void validate(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection) {

        final String ruby = params.getString("ruby");

        if (StringUtils.isEmpty(ruby)) {
            errorCollection.addError("ruby", "You must specify a ruby runtime");
        }

        final String targets = params.getString("targets");

        if (StringUtils.isEmpty(targets)) {
            errorCollection.addError("targets", "You must specify at least one target");
        }

    }
}
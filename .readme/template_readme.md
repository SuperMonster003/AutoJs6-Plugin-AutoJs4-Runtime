<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="{{ night_icon_url }}" />
      <img src="{{ icon_url }}" alt="{{ icon_alt }}" border="0" width="128" />
    </picture>
  </p>

  <p>{{ text_plugin_synopsis }}</p>

  <p>
    <a href="{{ repo_url }}/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/{{ repo_slug }}?label=Release"/></a>
    <a href="{{ repo_url }}/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/{{ repo_slug }}?color=A24232&label=Issues"/></a>
    <a href="{{ license_url }}"><img alt="GitHub license" src="https://img.shields.io/github/license/{{ repo_slug }}?color=534BAE&label=License"/></a>
  </p>
</div>

******

### {{ h3_languages }}

******

{{ p_languages_all_supported }}:

{{ placeholder_ul_languages_all_supported }}

******

### {{ h3_introduction }}

******

{{ p_introduction }}

******

### {{ h3_usage }}

******

{{ placeholder_usage_steps }}

> {{ p_usage_note }}

******

### {{ h3_quick_start }}

******

{{ p_quick_start_intro }}:

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

{{ p_quick_start_result }}

******

### {{ h3_compatibility }}

******

{{ placeholder_compatibility_points }}

******

### {{ h3_security }}

******

{{ placeholder_security_points }}

******

### {{ h3_plugin_interface }}

******

{{ p_plugin_interface }}:

```text
application id: {{ plugin_application_id }}
plugin id: {{ plugin_id }}
engine: {{ plugin_engine }}
variant: {{ plugin_variant }}
service action: {{ plugin_service_action }}
service category: {{ plugin_service_category }}
aidl interface: {{ plugin_aidl_interface }}
contract version: {{ plugin_contract_version }}
aidl methods: getInfo(), getRuntimeInfo(), runScript(request, callback), cancelScript(executionId)
minimum host build: {{ required_host_version_code }} ({{ required_host_version_name }})
embedded AutoJs4: {{ autojs4_version }} ({{ autojs4_commit }})
rhino: {{ rhino_version }}
supported abis: {{ supported_abis }}
max concurrent executions: {{ max_concurrent_executions }}
max log chunk: {{ max_log_chunk }}
```

{{ p_contract }}

{{ p_16kb_status }}

******

### {{ h3_release_history }}

******

{{ placeholder_latest_release_history }}

##### {{ h5_more_release_history }}

- {{ placeholder_read_more_in_changelog_md }}

******

### {{ h3_build }}

******

{{ p_build_intro }}

```powershell
git clone --recurse-submodules {{ repo_url }}.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### {{ h3_localization }}

******

{{ p_localization }}

```text
.readme/common.json
.readme/lang_*.json
.readme/template_readme.md
.readme/template_plugin_instruction.md
.changelog/lang_*.json
.changelog/template_changelog.md
.python/generate_markdown.py
app/src/main/assets/doc/CHANGELOG-*.md
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
```

{{ p_icon_artwork }}

******

### {{ h3_license }}

******

{{ p_license }}

******

### {{ h3_links }}

******

- {{ text_link_autojs6 }}: {{ autojs6_url }}
- {{ text_link_autojs4 }}: {{ autojs4_url }}
- {{ text_link_autojs4_docs }}: {{ autojs4_documentation_url }}
- {{ text_link_protocol }}: {{ protocol_doc_url }}
- {{ text_link_roadmap }}: {{ roadmap_url }}
- {{ text_link_third_party_notices }}: {{ third_party_notices_url }}

# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
### Changed
### Fixed

## [1.1.5] - 2019-9-21
### Added
- integrated with mustache template engine
- added <code>CompositeEngineProperties</code> for configurations
- added generic <code>Dao</code> type parameter of <code>JdbiMixin</code> to reduce single dao manipulations
- supported composite bean pre-loading
- added multi-data-source configuration <code>MultiDataSourceConfiguration</code>
- added exception handler for FSM
- supported <code>Modifier</code> of property: final, volatile, transient
- added <code>MustacheSqlLocator.FileLoader</code> to support configurable resource loader
- initialized GL demo
- introduced <code>WithXXX</code> pattern for mixins
### Changed
- upgraded bom to 1.2.6 & migrated to spring boot 2.0.5
### Fixed
- fixed bind list
- fixed lombok broken


## [1.1.3] - 2018-9-19
### Changed
- enhanced Printable to exclude fields and to set style
- upgraded jdbi to 3.3.0 & fixed <code>FreeMarkerSqlParser</code>
## [1.1.2] - 2018-9-15
### Changed
- upgraded jdbi to 3.1.0
## [1.1.1] - 2018-5-21
### Fixed
- merged template bug fixing
## [1.1.0] - 2018-5-21
### Added
- added <code>ClassVer</code> for generating <code>serialVersionUID</code> of <code>Serializable</code>
- added <code>Manager</code> to hold the static behaviors of <code>Entity</code>, and improved accounting demo
- added <code>QueryMixin</code>, <code>PageList</code> and <code>RepositoryMixn</code> to enhance DAL implementing
- added module engine to support module oriented development
- added controller decorator supporting
- added scope supporting for <code>ControllerDecorator</code>
- added <code>Mapper</code> for DTO mapping
- enhanced <code>CompositeUtil</code>
### Changed
- cleaned <code>Controller</code> related works, maybe try it later
- added the COP principle <b>Never use static</b> because it will break the extension mechanism of MDE
- default name() and packages() implements of  <code>IModule</code>
- removed meta property of <code>Composable</code> that can be accessed by <code>CompositeEngine.metaOf()</code>
- removed logback.xml and cleaned outdated message file
### Fixed
- fixed generic type property error
- fixed <code>Mapper</code>'s scope
- filtered dynamic properties during mapping of <code>Mapper</code>

## [1.0.2] - 2017-11-28
### Changed
- upgraded to jdbi 3.0.0

## [1.0.1] - 2017-11-27
### Added
- added scope supporting: singleton & prototype<br/>
in general, composite type don't need declare Scope Annotation explicitly in Spring Project,
but the scope of composite can be overwrite by explicit declaration.
- added <code>EntityHelper</code> for entities<br/>
EntityHelper supports general entity creating and singleton creating.
That entities will use singleton to implements global static behaviors likes findOne, remove .etc.
- added service engine that supports to declare services for modeling.<br/>
Service engine supports to combine with event engine to handle events.
- added <code>Dal</code> to improve global behaviors and transaction implementation.<br/>
<code>JdbiMixin</code> was deprecated now, and Repository layer was merged into Jdbi Dao.
### Changed
- created EntityPlugin to register mapper in global<br/>
that removed RegisterEntityRowMapper annotation per DAO
- removed *Rod and merged Dao into Repository<br/>
cleaning the tedious codes of global static functions of repository,
maybe in the future I will add RepositoryBase for common behaviors.
- supported <code>getCompositeSource, getCompositeName</code>.
- supported multiple <code>Initializer<code> to split initialization into mixins.</br>
The initializer priority follows interface inheritance hierarchy rule.
- set the package scanner filter only includes "*.class".
- migrated <code>Date</code> to <code>LocalDateTime</code> of <code>Auditable<code/>
### Fixed
- fixed example logging files to follow project settings.
- fixed <code>EntityMapper</code> to retrieve correct properties.
- fixed <code>EntityMapper</code> to use enhanced type of Spring.

## [1.0.0] - 2017-09-20
### Added
- **Core** the core of the MDE that implemented **COP** principle,
it supports composite, mixin and extension definition and assembling.
- Entity-Engine, Event-Engine, FSM-Engine and Spring boot starters.


# Appendixes
Principles:
- Changelogs are for humans, not machines.
- There should be an entry for every single version.
- The same types of changes should be grouped.
- Versions and sections should be linkable.
- The latest version comes first.
- The release date of each versions is displayed.
- Mention whether you follow Semantic Versioning.

Types of changes:
- **Added** for new features.
- **Changed** for changes in existing functionality.
- **Deprecated** for soon-to-be removed features.
- **Removed** for now removed features.
- **Fixed** for any bug fixes.
- **Security** in case of vulnerabilities.

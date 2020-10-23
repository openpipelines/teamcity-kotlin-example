import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

version "2019.2"

project {
    vcsRoot(TeamcityKotlinExampleVcs)
    buildType(Build)
}

object Build : BuildType({
    name = "Lint"
    vcs {
        root(TeamcityKotlinExampleVcs)
    }
    steps {
        dockerCommand {
            name = "lint"
            commandType = other {
                subCommand = "run"
                commandArgs = "--rm -v %teamcity.build.checkoutDir%:/build golangci/golangci-lint:latest golangci-lint version"
            }
        }
    }
    triggers {
        vcs {
        }
    }
})

object TeamcityKotlinExampleVcs : GitVcsRoot({
    name = "TeamcityKotlinExampleVcs"
    url = "https://github.com/openpipelines/teamcity-kotlin-example"
    branch = "refs/heads/main"
})

package com.itemis.maven.plugins.unleash.steps.actions;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.deployment.DeploymentException;

import com.itemis.maven.aether.ArtifactDeployer;
import com.itemis.maven.plugins.cdi.CDIMojoProcessingStep;
import com.itemis.maven.plugins.cdi.annotations.ProcessingStep;
import com.itemis.maven.plugins.unleash.ReleaseMetadata;
import com.itemis.maven.plugins.unleash.util.MavenLogWrapper;

@ProcessingStep(id = "deployArtifacts", description = "Deploys the release artifacts to the remote repository.")
public class DeployArtifacts implements CDIMojoProcessingStep {
  @Inject
  private MavenLogWrapper log;

  @Inject
  @Named("reactorProjects")
  private List<MavenProject> reactorProjects;

  @Inject
  private ArtifactDeployer deployer;

  @Inject
  private ReleaseMetadata metadata;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    this.log.info("Deploying the release artifacts into the distribution repository");

    try {
      this.deployer.deployArtifacts(this.metadata.getReleaseArtifacts());
    } catch (DeploymentException e) {
      throw new MojoFailureException("Unable to deploy artifacts into remote repository.", e);
    }
  }
}
package com.dropwizard.applictaion;

import com.dropwizard.config.BasicConfiguration;
import com.dropwizard.controller.ApplicationHealthCheck;
import com.dropwizard.controller.BrandResource;
import com.dropwizard.domain.Brand;
import com.dropwizard.domain.BrandRepository;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.ArrayList;
import java.util.List;

public class IntroductionApplication extends Application<BasicConfiguration> {

    public static void main(String[] args) throws Exception {
        new IntroductionApplication().run("server", "introduction-config.yml");
    }

    @Override
    public void run(BasicConfiguration basicConfiguration, Environment environment) {
        int defaultSize = basicConfiguration.getDefaultSize();
        BrandRepository brandRepository = new BrandRepository(initBrands());
        BrandResource brandResource = new BrandResource(defaultSize, brandRepository);

        environment
                .healthChecks()
                .register("application", new ApplicationHealthCheck());

        environment
                .jersey()
                .register(brandResource);
    }

    @Override
    public void initialize(Bootstrap<BasicConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }

    private List<Brand> initBrands() {
        final List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1L, "Brand1"));
        brands.add(new Brand(2L, "Brand2"));
        brands.add(new Brand(3L, "Brand3"));

        return brands;
    }
}
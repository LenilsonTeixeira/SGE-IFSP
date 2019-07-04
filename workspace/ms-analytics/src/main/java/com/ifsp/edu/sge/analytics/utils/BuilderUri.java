package com.ifsp.edu.sge.analytics.utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class BuilderUri {

    public UriComponents buildUri(final String scheme, final String host, final String port, final String path){
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .build();
    }
}

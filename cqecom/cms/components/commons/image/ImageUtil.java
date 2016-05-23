package com.cqecom.cms.components.commons.image;


import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

public class ImageUtil {

    public static void setImageDimensions(Image image, ResourceResolver resolver, int defaultHeight, int defaultWidth) {
        if (StringUtils.isBlank(image.getReference())) {
            throw new IllegalArgumentException("Object doesn't reference any image");
        }

        String path = image.getReference() + "/jcr:content/metadata";
        Resource metadataResource = resolver.getResource(path);

        if (metadataResource == null) {
            image.setHeight(defaultHeight);
            image.setWidth(defaultWidth);
        } else {
            ValueMap metadata = metadataResource.adaptTo(ValueMap.class);
            image.setHeight(metadata.get("tiff:ImageLength", defaultHeight));
            image.setWidth(metadata.get("tiff:ImageWidth", defaultWidth));
        }

    }
}

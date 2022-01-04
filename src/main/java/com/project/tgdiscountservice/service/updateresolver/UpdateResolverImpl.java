package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateResolverImpl extends TelegramUpdateResolver {

    private final List<TelegramUpdateResolver> resolvers;

    @Override
    public void prepareMessage(InnerUpdate update) {
        resolvers.forEach(service -> service.prepareMessage(update));
    }
}

package nrg.inc.synhubbackend.analytics.infrastructure.client;

import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.MemberResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "member-service", url = "${member.service.url}")
public interface MemberClient {
    @GetMapping("/api/v1/members/group/{groupId}")
    List<MemberResource> getMembersByGroupId(@PathVariable Long groupId);
}
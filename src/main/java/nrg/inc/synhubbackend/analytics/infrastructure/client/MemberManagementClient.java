package nrg.inc.synhubbackend.analytics.infrastructure.client;

import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.MemberResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "memberManagementClient",
        url = "http://localhost:8080/api/v1" // Hardcoded base URL
)
public interface MemberManagementClient {

    @GetMapping("/members/group/{groupId}")
    List<MemberResource> getMembersByGroupId(@PathVariable Long groupId);
}
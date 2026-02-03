package com.jnthn.accord_iam.playground.admin.web

import com.jnthn.accord_iam.playground.admin.service.PlaygroundAdminService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/admin/projects/{projectId}/playground")
class PlaygroundAdminController(
    private val playgroundAdminService: PlaygroundAdminService
) {

    /**
     * Create a playground OAuth client for this project.
     * Raw secret is returned ONCE.
     */
    @PostMapping("/client")
    fun createPlaygroundClient(
        @PathVariable projectId: UUID
    ) = playgroundAdminService.createPlaygroundClient(projectId)

    /**
     * Get playground status (used by frontend to know if client exists).
     */
    @GetMapping
    fun getPlaygroundStatus(
        @PathVariable projectId: UUID
    ) = playgroundAdminService.getPlaygroundStatus(projectId)

    /**
     * Delete the playground client (allows regeneration).
     */
    @DeleteMapping("/client")
    fun deletePlaygroundClient(
        @PathVariable projectId: UUID
    ) {
        playgroundAdminService.deletePlaygroundClient(projectId)
    }
}
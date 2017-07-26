package net.benwoodworth.fastcraft.impl.sponge.permission

import dagger.Module
import dagger.Provides
import net.benwoodworth.fastcraft.dependencies.permission.ModulePermission
import net.benwoodworth.fastcraft.dependencies.permission.Permission

/**
 * Sponge implementation of [ModulePermission].
 */
@Module
class SpongeModulePermission : ModulePermission {

    @Provides
    override fun permissionBuilder(): Permission.Builder {
        TODO("not implemented")
    }
}

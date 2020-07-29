import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RoomManagementSharedModule } from 'app/shared/shared.module';
import { RoomManagementCoreModule } from 'app/core/core.module';
import { RoomManagementAppRoutingModule } from './app-routing.module';
import { RoomManagementHomeModule } from './home/home.module';
import { RoomManagementEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RoomManagementSharedModule,
    RoomManagementCoreModule,
    RoomManagementHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RoomManagementEntityModule,
    RoomManagementAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class RoomManagementAppModule {}

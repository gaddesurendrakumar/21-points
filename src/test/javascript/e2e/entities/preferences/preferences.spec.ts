import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PreferencesComponentsPage } from './preferences.page-object';

const expect = chai.expect;

describe('Preferences e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let preferencesComponentsPage: PreferencesComponentsPage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Preferences', async () => {
    await navBarPage.goToEntity('preferences');
    preferencesComponentsPage = new PreferencesComponentsPage();
    await browser.wait(ec.visibilityOf(preferencesComponentsPage.title), 5000);
    expect(await preferencesComponentsPage.getTitle()).to.eq('twentyOnePointsApp.preferences.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(preferencesComponentsPage.entities), ec.visibilityOf(preferencesComponentsPage.noResult)),
      1000
    );
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

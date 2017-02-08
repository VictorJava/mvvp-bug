/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jhordan.people_mvvm;

import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.view.View;

import test.common.model.Name;
import test.common.model.People;
import test.common.model.Picture;
import com.example.jhordan.people_mvvm.viewmodel.ItemPeopleViewModel;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Notes for Mac!!
 * <p>
 * If you are on a Mac, you will probably need to configure the
 * default JUnit test runner configuration in order to work around a bug where IntelliJ / Android
 * Studio
 * does not set the working directory to the module being tested. This can be accomplished by
 * editing
 * the run configurations, Defaults -> JUnit and changing the working directory value to
 * $MODULE_DIR$
 * <p>
 * You have to specify  sdk < 23 (Robolectric does not support API level 23.)
 * <p>
 * https://github.com/robolectric/robolectric/issues/1648
 **/

@RunWith(RobolectricGradleTestRunner.class) @Config(constants = BuildConfig.class, sdk = 21)
public class ItemPeopleViewModelTest {

  private static final String PEOPLE_CELL_TEST = "0177-6155420";
  private static final String PEOPLE_MAIL_TEST = "theodor.kaufmann@example.com";
  private static final String PEOPLE_PICTURE_TEST =
      "http://api.randomuser.me/portraits/women/39.jpg";
  private static final String PEOPLE_TITLE_TEST = "ms";
  private static final String PEOPLE_FIRST_TEST = "constance";
  private static final String PEOPLE_LAST_TEST = "fowler";

  private PeopleApplication peopleApplication;

  @Before public void setUpItemPeopleModelTest() {
    peopleApplication = (PeopleApplication) RuntimeEnvironment.application;
  }

  @Test public void shouldGetPeopleCell() throws Exception {
    People people = new People();
    people.cell = PEOPLE_CELL_TEST;
    ItemPeopleViewModel itemPeopleViewModel = new ItemPeopleViewModel(people, peopleApplication);
    assertEquals(people.cell, itemPeopleViewModel.getCell());
  }

  @Test public void shouldGetPeopleMail() throws Exception {
    People people = new People();
    people.mail = PEOPLE_MAIL_TEST;
    ItemPeopleViewModel itemPeopleViewModel = new ItemPeopleViewModel(people, peopleApplication);
    assertEquals(people.mail, itemPeopleViewModel.getMail());
  }

  @Ignore public void shouldGetPeoplePicture() throws Exception {
    People people = new People();
    people.picture = Mockito.mock(Picture.class);
    people.picture.large = PEOPLE_PICTURE_TEST;
    ItemPeopleViewModel itemPeopleViewModel = new ItemPeopleViewModel(people, peopleApplication);
    assertEquals(people.picture.large, itemPeopleViewModel.getPictureProfile());
  }

  @Test public void shouldGetPeopleFullName() throws Exception {
    People people = new People();
    people.name = Mockito.mock(Name.class);
    people.name.title = PEOPLE_TITLE_TEST;
    people.name.firts = PEOPLE_FIRST_TEST;
    people.name.last = PEOPLE_LAST_TEST;
    people.fullName = people.name.title + "." + people.name.firts + " " + people.name.last;
    ItemPeopleViewModel itemPeopleViewModel = new ItemPeopleViewModel(people, peopleApplication);
    assertEquals(people.fullName, itemPeopleViewModel.getFullName());
  }

  @Test public void shouldStartPeopleDetailActivityOnItemClick() throws Exception {
    People people = new People();
    Context mockContext = mock(Context.class);
    ItemPeopleViewModel itemPeopleViewModel = new ItemPeopleViewModel(people, mockContext);
    itemPeopleViewModel.onItemClick(new View(peopleApplication));
    verify(mockContext).startActivity(any(Intent.class));
  }

  @Test public void shouldNotifyPropertyChangeWhenSetPeople() throws Exception {
    People people = new People();
    ItemPeopleViewModel itemPeopleViewModel = new ItemPeopleViewModel(people, peopleApplication);
    Observable.OnPropertyChangedCallback mockCallback =
        mock(Observable.OnPropertyChangedCallback.class);
    itemPeopleViewModel.addOnPropertyChangedCallback(mockCallback);
    itemPeopleViewModel.setPeople(people);
    verify(mockCallback).onPropertyChanged(any(Observable.class), anyInt());
  }
}

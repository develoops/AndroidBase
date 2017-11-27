package fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import mc.sms.R;
import mc.sms.myApp;
import model.ActContAct;
import model.Actividad;

import model.Media;
import model.Org;
import model.PersonaRolAct;
import model.PersonaRolOrg;
import model.PreguntaEncuesta;
import utils.MUtil;

/**
 * Created by Alvaro on 2/25/15.
 */
public class LoadDataFragment extends Fragment {

	//public static List<TabUI> tabUIs;
	public static List<Actividad> actividades;
	public static List<Actividad> eventos;
	public static List<Org> orgs;
	public static List<PersonaRolOrg> staff, academic;
	//public static Stand stand, mCongress;
	public myApp myapp;
	//public static CompanyApp company;
	public static ParseImageView splash;
	public static Integer i = 0;
	public static ProgressBar bar;
	public static Boolean flag = false;
	public static View RootView;
	public static Org com, mobiCongress;
	//public static Facade facade;

	public static LoadDataFragment newInstance() {
		// Instantiate a new fragment
		LoadDataFragment fragment = new LoadDataFragment();
		fragment.setRetainInstance(true);
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.myapp = (myApp) getActivity().getApplicationContext();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Retain this fragment across configuration changes.
		setRetainInstance(true);
		if (myapp.isFirstTime()) {
			if (!myapp.checkConnection()) {

				return;
			}
			else {

				Log.e(getClass().getName(), "first time load data");
				loadDataAndUpdateLocal();
				myapp.setFirstTime();
			}

		}
		else {

				loadDataAndUpdateLocal();

		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View RootView = inflater.inflate(R.layout.splash_layout, container, false);
		bar = (ProgressBar) RootView.findViewById(R.id.progressBar);
		ImageView splash_first = (ImageView) RootView.findViewById(R.id.splash_first);
		splash_first.setVisibility(View.VISIBLE);
		return RootView;
	}

	//From second time:
	// if connection available, load server data, update local
	// if not, load local data, finish.
	private void loadDataAndUpdateLocal() {



		Log.e(getClass().getName(), "call loadServerDataAndSaveLocal");

		MUtil.isUpdateLocal = true;


		ParseQuery<Actividad> queryContenido = ParseQuery.getQuery(Actividad.class);

		queryContenido.include("lugar");
		queryContenido.setLimit(1000);
		if (!myapp.checkConnection()) {
			queryContenido.fromLocalDatastore();

		}
		queryContenido.whereNotEqualTo("congreso","tipo");
		queryContenido.findInBackground(new FindCallback<Actividad>() {
			@Override
			public void done(final List<Actividad> objects, ParseException e) {
				if(objects!=null){
					eventos = objects;
					Log.i("EVENTOSSSSS11111",String.valueOf(eventos.size()));

					ParseObject.pinAllInBackground("actividades",objects);
				}
				else {
					Log.i("NULO","NULO");
				}


			}
		});

		ParseQuery<Media> queryMedia = ParseQuery.getQuery(Media.class);
		queryMedia.findInBackground(new FindCallback<Media>() {
			@Override
			public void done(List<Media> objects, ParseException e) {
				ParseObject.pinAllInBackground("media",objects);
			}
		});

		ParseQuery<ActContAct> queryCont = ParseQuery.getQuery(ActContAct.class);
		if (!myapp.checkConnection()) {
			queryCont.fromLocalDatastore();

		}
		queryCont.include("contenido.lugar");
		queryCont.include("contenedor");
		queryCont.setLimit(1000);
		//queryCont.whereEqualTo("contenedor", actividad);

		queryCont.findInBackground(new FindCallback<ActContAct>() {
			@Override
			public void done(List<ActContAct> objects, ParseException e) {
				if(objects!=null){
					ParseObject.pinAllInBackground("ActconAct2", objects);
				}
				else {
					Log.i("NULO2","NULO");
				}

			}
		});




		ParseQuery<PersonaRolOrg> queryPersonaRolOrg = ParseQuery.getQuery(PersonaRolOrg.class);

		queryPersonaRolOrg.include("persona.pais");
		queryPersonaRolOrg.include("org");
		if (!myapp.checkConnection()) {
			queryPersonaRolOrg.fromLocalDatastore();

		}
		queryPersonaRolOrg.whereEqualTo("tipo","sociedad");

		queryPersonaRolOrg.findInBackground(new FindCallback<PersonaRolOrg>() {
			@Override
			public void done(List<PersonaRolOrg> objects, ParseException e) {
				if(objects!=null){
					staff = objects;
					ParseObject.pinAllInBackground("sociedad",objects);
				}
				else {
					Log.i("DIRECTIVA","NULO");
				}

			}
		});


		ParseQuery<PersonaRolOrg> queryPersonaRolOrg2 = ParseQuery.getQuery(PersonaRolOrg.class);
		queryPersonaRolOrg2.include("persona.pais");
		queryPersonaRolOrg2.include("org");
		queryPersonaRolOrg2.whereEqualTo("tipo","congreso");
		queryPersonaRolOrg2.findInBackground(new FindCallback<PersonaRolOrg>() {
			@Override
			public void done(List<PersonaRolOrg> objects, ParseException e) {
				if(objects!=null){


					academic = objects;
				}

				ParseObject.pinAllInBackground("comite",objects);

			}
		});

		ParseQuery<Org> queryOrg = ParseQuery.getQuery(Org.class);
		if (!myapp.checkConnection()) {
			queryOrg.fromLocalDatastore();

		}
		queryOrg.whereEqualTo("tipo","sociedad");

		queryOrg.getFirstInBackground(new GetCallback<Org>() {
			@Override
			public void done(Org object, ParseException e) {
				if(object!=null){
					com= object;
					object.pinInBackground("sociedad");
				}
				else {
					Log.i("SOCIEDAD","NULO");
				}
			}
		});

		ParseQuery<PreguntaEncuesta> preguntaEncuestaParseQuery = ParseQuery.getQuery(PreguntaEncuesta.class);

		preguntaEncuestaParseQuery.findInBackground(new FindCallback<PreguntaEncuesta>() {
			@Override
			public void done(List<PreguntaEncuesta> objects, ParseException e) {

					ParseObject.pinAllInBackground(objects);
			}
		});

		ParseQuery<Org> queryOrg2 = ParseQuery.getQuery(Org.class);
		if (!myapp.checkConnection()) {
			queryOrg2.fromLocalDatastore();

		}
		queryOrg2.whereEqualTo("tipo","patrocinador");

		queryOrg2.findInBackground(new FindCallback<Org>() {
			@Override
			public void done(List<Org> objects, ParseException e) {
				if(objects!=null){

					/*for(Org org:objects){
						org.getimgPerfil().getDataInBackground();
					}*/
					orgs= objects;
					Log.i("CANTIDADPATR", String.valueOf(orgs.size()));
					ParseObject.pinAllInBackground("patrocinadores",objects);
				}

			}
		});


		ParseQuery<PersonaRolAct> queryPersonaRolAct = ParseQuery.getQuery(PersonaRolAct.class);
		queryPersonaRolAct.include("act.lugar");
		queryPersonaRolAct.include("congreso.lugar");
		queryPersonaRolAct.include("persona.pais");
		if (!myapp.checkConnection()) {
			queryPersonaRolAct.fromLocalDatastore();

		}
		queryPersonaRolAct.findInBackground(new FindCallback<PersonaRolAct>() {
			@Override
			public void done(List<PersonaRolAct> objects, ParseException e) {
				if(objects!=null){
					/*for(PersonaRolAct personaRolOrg:objects){
						personaRolOrg.getPerson().getImage().getDataInBackground();
					}*/
					ParseObject.pinAllInBackground("personasRol",objects);
				}
				else {
					Log.i("NULO4","NULO");
				}
			}
		});


/*
		ParseQuery<Media> queryMedia = ParseQuery.getQuery(Media.class);
		queryMedia.findInBackground(new FindCallback<Media>() {
			@Override
			public void done(List<Media> objects, ParseException e) {
				ParseObject.pinAllInBackground("media",objects);
			}
		});*/

		ParseQuery<Actividad> query = ParseQuery.getQuery(Actividad.class);
		query.include("lugar");
		if (!myapp.checkConnection()) {
			query.fromLocalDatastore();

		}
		query.whereEqualTo("tipo","congreso");
		query.findInBackground(
				new FindCallback<Actividad>() {
					@Override
					public void done(final List<Actividad> activities, ParseException e) {

						if(activities!=null){
							actividades=activities;


							for (Actividad actividad : actividades) {
								actividad.getParseFileV1().getDataInBackground();
								ParseQuery<ActContAct> queryCont = ParseQuery.getQuery(ActContAct.class);
								queryCont.include("contenido.lugar");
								queryCont.include("contenedor");
								if (!myapp.checkConnection()) {
									queryCont.fromLocalDatastore();

								}
								queryCont.whereEqualTo("contenedor", actividad);
								queryCont.findInBackground(new FindCallback<ActContAct>() {
									@Override
									public void done(List<ActContAct> objects, ParseException e) {
										if(objects!=null){
											ParseObject.pinAllInBackground("ActConAct", objects);

										}
									}
								});
							}
						}

						else {
							Log.i("NULO5","NULO");
						}

//							if (facade1.getRole().equals("mCongress")) {
//								mobiCongress = facade1.getCompany();
//								mobiCongress.getheaderImage().getParseFileV1().getDataInBackground();
//								mobiCongress.getLogo().getParseFileV1().getDataInBackground();
//								//com.getLogo().getParseFileV1().getDataInBackground();
//								if (mobiCongress.getFiles() != null) {
//									files = mobiCongress.getFiles();
//								}
//							}*/
//						}
//						meetingApps = company.getMeetingApps();
//						for (MeetingApp meetingApp : meetingApps) {
//                            if(meetingApp.getIcon()!=null){
//                                meetingApp.getIcon().getParseFileV1().getDataInBackground();
//                            }
//							if(meetingApp.getSplashMeeting()!=null){
//                                meetingApp.getSplashMeeting().getParseFileV1().getDataInBackground();
//                            }
//
//                            if(meetingApp.getCompaniesFacade()!=null){
//                                for (Facade facade1 : meetingApp.getCompaniesFacade()) {
//                                    if(facade1.getCompany().getLogo()!=null){
//                                        if(facade1.getCompany().getLogo().getParseFileV1()!=null){
//                                            facade1.getCompany().getLogo().getParseFileV1().getDataInBackground();
//                                        }
//                                    }
//
//                                    if(facade1.getCompany().getheaderImage()!=null){
//                                        if(facade1.getCompany().getheaderImage().getParseFileV1()!=null){
//                                            facade1.getCompany().getheaderImage().getParseFileV1().getDataInBackground();
//                                        }
//                                    }
//
//
//
//                                }
//                            }
//
//						}

						final Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								 newFragment();
							   }

						},5000);

					}
				}

		);
	}

	//First time: Load server data and save to local.



	private void newFragment() {
		MUtil.stopTimer(getClass().getName());

			Log.i("boolean", String.valueOf(myapp.isFirstTime()));
			myapp.setSecondTime();
			ViewPagerFragment loadDataFragment = new ViewPagerFragment();
			if (getActivity() != null) {

				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, loadDataFragment, "ViewPager");
				ft.commitAllowingStateLoss();
				bar.setVisibility(View.INVISIBLE);
			}
			else {
			}

	}

/*
private void firstDataLoad(){



     //Download data from active CompanyApp
     ParseQuery<CompanyApp> query = ParseQuery.getQuery(CompanyApp.class);

     query.include("aboutCompany");
     query.include("aboutCompany.company");
     query.include("aboutCompany.company.place");
     query.include("companySplash");
     query.include("companyFiles");
     query.include("mCongress");
     query.include("mCongress.company");
     query.include("mCongress.company.place");
     query.include("palette");
     query.include("staffCompany");
     query.include("staffCompany.person");
     query.include("staffCompany.company");
     query.include("tabBarCompany.tabs");
     query.include("tabBarCompany.tabs.viewPointer");

     query.include("meetingApps");
     query.include("meetingApps.events");
     query.include("meetingApps.events.actors");
     query.include("meetingApps.events.actors.events");
     query.include("meetingApps.events.actors.person");
     query.include("meetingApps.events.actors.person.place");
     query.include("meetingApps.events.actors.company");
     query.include("meetingApps.events.anidateEvents");
     query.include("meetingApps.events.anidateEvents.actors");
     query.include("meetingApps.events.anidateEvents.actors.person");
     query.include("meetingApps.events.anidateEvents.actors.person.place");
     query.include("meetingApps.events.anidateEvents.library");
     query.include("meetingApps.events.anidateEvents.gallery");
     query.include("meetingApps.events.anidateEvents.place");
     query.include("meetingApps.events.anidateEvents.place.map");
     query.include("meetingApps.events.anidateEvents.palette");

     query.include("meetingApps.events.library");
     query.include("meetingApps.events.gallery");
     query.include("meetingApps.events.place.map");
     query.include("meetingApps.events.toolBar");
     query.include("meetingApps.events.place");
     query.include("meetingApps.events.map");

     query.include("meetingApps.companies");
     query.include("meetingApps.companies.stand");
     query.include("meetingApps.library");
     query.include("meetingApps.news");
     query.include("meetingApps.palette");

     query.include("meetingApps.persons");
     query.include("meetingApps.persons.actors");
     query.include("meetingApps.persons.actors.person");
     //query4.include("meetingApps.persons.actors.person.place");
     query.include("meetingApps.persons.actors.company");

     query.include("meetingApps.place");
     query.include("meetingApps.polls");
     query.include("meetingApps.splashMeeting");

     query.include("meetingApps.stands");
     query.include("meetingApps.stands.company");
     query.include("meetingApps.stands.company.place");

     query.include("meetingApps.subMeetings");
     query.include("meetingApps.tabBarMeeting.tabs");
     query.include("meetingApps.tabBarMeeting.tabs.viewPointer");



     query.whereEqualTo("active",true);
     query.getFirstInBackground(new GetCallback<CompanyApp>() {
         @Override
         public void done(final CompanyApp companyApp, ParseException e) {
             if(e==null){


                 company = companyApp;
                 meetingApps = companyApp.getMeetingApps();
                 if(companyApp.getTabBarUI()!=null){
                     tabUIs = companyApp.getTabBarUI().getTabs();
                 }
                 else {

                 }
                 if(companyApp.getAboutCompany()!=null){
                     stand = companyApp.getAboutCompany();
                 }
                 if(companyApp.getStaffCompany()!=null){
                     staff = companyApp.getStaffCompany();

                 }

                 if(companyApp.getFiles()!=null){
                     files = companyApp.getFiles();
                 }
                 for(MeetingApp meetingApp:meetingApps){

         Log.i("RATE",meetingApp.getNameParse());
         if(meetingApp.getIconParse()!=null){
             meetingApp.getIconParse().getDataInBackground();
         }
         if(meetingApp.getSplash()!=null){
             meetingApp.getSplash().getParseFile().getDataInBackground();
         }


         List <Event> events = meetingApp.getEvents();
         if(events!=null){
             for(Event event:events){
                 if(event.getEventProfilePicture()!=null){
                     event.getEventProfilePicture().getDataInBackground();
                 }
                 else {

                 }


             }
         }

     }

     if(staff != null){
         for(Actor actor:staff){
             if(actor.getPerson().getProfilePicture()!=null){
                 actor.getPerson().getProfilePicture().getDataInBackground();
             }




         }

     }

     if(companyApp.getAboutCompany()!=null){
         companyApp.getAboutCompany().getCompanyHeader().getDataInBackground();
     }

     if(companyApp.getAboutCompany()!=null){
         companyApp.getAboutCompany().getCompanyLogo().getDataInBackground();
     }


     if(companyApp.getCompanySplash().getParseFile()!=null){
         companyApp.getCompanySplash().getParseFile().getDataInBackground();
     }




     companyApp.unpinInBackground("company",new DeleteCallback() {
         @Override
         public void done(ParseException e) {
             companyApp.pinInBackground("company", new SaveCallback() {
                 @Override
                 public void done(ParseException e) {

                 }
             });
         }
     });


     newFragment();


 }
 else {

 }
}
});



     ParseQuery<Person> query2 = ParseQuery.getQuery(Person.class);
     query2.include("actors");
     query2.findInBackground(new FindCallback<Person>() {
         @Override
         public void done(final List<Person> persons, ParseException e) {
             Person.unpinAllInBackground("persons", new DeleteCallback() {
                 @Override
                 public void done(ParseException e) {
                     Person.pinAllInBackground("persons", persons);
                 }
             });
             for(Person person:persons){
                 if(person.getProfilePicture()!=null){
                     person.getProfilePicture().getDataInBackground();
                 }

             }
         }
     });

     ParseQuery<Event> query3 = ParseQuery.getQuery(Event.class);
     query3.include("anidateEvents");
     query3.include("toolBar");
     query3.include("map");
     query3.include("place.map");

     query3.findInBackground(new FindCallback<Event>() {
         @Override
         public void done(final List<Event> events, ParseException e) {
             Event.unpinAllInBackground("events", new DeleteCallback() {
                 @Override
                 public void done(ParseException e) {
                     Event.pinAllInBackground("events",events);
                 }
             });
         }
     });

     ParseQuery<Image> image= ParseQuery.getQuery(Image.class);

     image.findInBackground(new FindCallback<Image>() {
         @Override
         public void done(final List<Image> images, ParseException e) {
             Image.unpinAllInBackground("images", new DeleteCallback() {
                 @Override
                 public void done(ParseException e) {
                     Image.pinAllInBackground("images",images);
                 }
             });
         }
     });


 }

*/
}


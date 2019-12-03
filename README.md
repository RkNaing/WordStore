# Word Store

Words store is a free form, light-weight and short to the point self vocabulary builder application to beef up your vocabulary.

<p align="center">
<a
    href='https://play.google.com/store/apps/details?id=com.devshub.rk.wordsstore&hl=en&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'>
	<img
        alt='Get it on Google Play'
        src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="162" height="60" />
</a>
</p>

## Technical Features

1. Crafted UI with the new [Google's Material Design Library](https://material.io/components/).
2. Surving configuration changes and separation of business logic from UI code by using [Lifecycle-Aware Components](https://developer.android.com/topic/libraries/architecture/lifecycle).
3. Local data persistent using [Room](https://developer.android.com/topic/libraries/architecture/room).
4. Graceful handling of asynchronous operation to achieve a lag-free UI using [Kotlin's Coroutine](https://kotlinlang.org/docs/reference/coroutines-overview.html).
5. Provided [Night(Dark)](https://developer.android.com/guide/topics/ui/look-and-feel/darktheme) UI to have an ease experience of devices battery and user's eyes.
6. User defined periodic "Word of The Day" notification with [Work Manager](https://developer.android.com/topic/libraries/architecture/workmanager).
7. An effort(although not a full-proof one which is insolvable) to keep the [Work Manager](https://developer.android.com/topic/libraries/architecture/workmanager) working in OEM ROMs(Huawei, Xiaomi, Oppo, Vivo, etc) by using [AutoStarter](https://github.com/judemanutd/AutoStarter) recommended by [dontkillmyapp.com](https://dontkillmyapp.com/).
8. Graceful loading of records from [Room](https://developer.android.com/topic/libraries/architecture/room) using [Paging Library](https://developer.android.com/topic/libraries/architecture/paging) to have an ease on device memory.
9. Included [Google Firebase Analytics](https://firebase.google.com/docs/analytics) and [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics). Please include project google-services.json of your Firebase project under **app** directory.

## License

The source codes are open sourced and can be used for personal or commercial products. Please be aware to make modifications according to your needs and contributor is not responsible for consequnces of blind plugged in use.
